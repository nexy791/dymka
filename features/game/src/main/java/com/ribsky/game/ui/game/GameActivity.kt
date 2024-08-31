package com.ribsky.game.ui.game

import android.graphics.PorterDuff
import android.os.CountDownTimer
import android.view.MotionEvent
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.Payload
import com.google.android.gms.nearby.connection.PayloadTransferUpdate
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.analytics.Analytics
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.common.utils.ext.ViewExt.Companion.vibrate
import com.ribsky.common.utils.glide.ImageLoader.Companion.loadImage
import com.ribsky.core.Resource
import com.ribsky.core.utils.DateUtils.Companion.formatDateMMSS
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.game.adapter.test.TestAdapter
import com.ribsky.game.databinding.ActivityGameBinding
import com.ribsky.game.manager.connection.ConnectionManager
import com.ribsky.game.manager.game.GameManager
import com.ribsky.game.manager.game.GameManagerImpl
import com.ribsky.game.model.PayLoadModel
import com.ribsky.navigation.features.GameNavigation
import com.ribsky.navigation.features.GameNavigation.Companion.KEY_GAME_END_POINT_ID
import com.ribsky.navigation.features.GameNavigation.Companion.KEY_GAME_TEST_ID
import com.ribsky.navigation.features.GameNavigation.Companion.KEY_GAME_USER_STATUS
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class GameActivity :
    BaseActivity<GameViewModel, ActivityGameBinding>(ActivityGameBinding::inflate) {

    override val viewModel: GameViewModel by viewModel()
    private val connectionManager: ConnectionManager by inject()
    private val gameManager: GameManager by lazy {
        GameManagerImpl(endPointId, connectionManager)
    }

    private val endPointId: String by lazy {
        intent.getStringExtra(KEY_GAME_END_POINT_ID)!!
    }

    private val userStatus: GameNavigation.UserStatus by lazy {
        intent.getParcelableExtra(KEY_GAME_USER_STATUS)!!
    }

    private val testId: String by lazy {
        intent.getStringExtra(KEY_GAME_TEST_ID) ?: ""
    }

    private var adapter: TestAdapter? = null
    private var isLoading = false

    private var timer: CountDownTimer? = null

    private val storage: FirebaseStorage by KoinJavaComponent.inject(FirebaseStorage::class.java)

    private val callback = object : ConnectionManager.ConnectionPayLoadListener {
        override fun onPayLoadReceived(payload: Payload) {
            if (payload.type == Payload.Type.BYTES) {
                val json = payload.asBytes()?.let { String(it, Charsets.UTF_8) }
                json?.let { viewModel.processPayLoad(it) }
            }
        }

        override fun onPayloadTransferUpdate(update: PayloadTransferUpdate) {
        }
    }

    private val connectionLifecycleCallback: ConnectionManager.ConnectionListener =
        object : ConnectionManager.ConnectionListener {
            override fun onConnectionInitiated(endpointId: String, connectionInfo: ConnectionInfo) {
            }

            override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            }

            // TODO: show dialog on back activity
            override fun onDisconnected(endpointId: String) {
                try {
                    showErrorDialog("Гравець вийшов з гри") { finish() }
                } catch (ex: Exception) {
                    finish()
                }
            }
        }

    private val connectionResult: ConnectionManager.ResultCallback =
        object : ConnectionManager.ResultCallback {
            override fun onSuccess() {
            }

            override fun onFailure(error: Exception) {
            }
        }

    override fun initView() = with(binding) {
        Analytics.logEvent(Analytics.Event.START_GAME)
        initConnection()
        initBtns()
        initAdapter()
        initRecyclerView()
        updateUiByStatus(GameStatus.NONE)
    }

    private fun updateUiByStatus(status: GameStatus) {
        when (status) {
            GameStatus.NONE -> showLoading()
            GameStatus.YOU_READY -> showWaiting()
            GameStatus.IN_GAME -> {
                viewModel.resetScore()
                initTimer()
                showContent()
            }

            GameStatus.FINISHED -> {
                timer?.cancel()
                getWinner()
            }

            else -> {}
        }
    }

    private fun updateScore(scoreHost: Int?, scoreQuest: Int?) = with(binding) {
        tvScore.text = "${scoreHost ?: 0} - ${scoreQuest ?: 0}"
    }

    private fun updateUsersUi(userHost: PayLoadModel.User?, userQuest: PayLoadModel.User?) =
        with(binding) {
            userHost?.let { ivAccount.loadImage(it.photo) }
            userQuest?.let { ivAccountOther.loadImage(it.photo) }
        }

    private fun initConnection() {
        connectionManager.apply {
            setConnectionPayLoadListener(callback)
            setConnectionListener(connectionLifecycleCallback)
        }
    }

    override fun initObs() = with(viewModel) {
        setHost(userStatus.isHost())
        setTestId(testId)
        getProfile()

        userHostStatus.observe(this@GameActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUsersUi(userHost, userQuest)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
        userQuestStatus.observe(this@GameActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUsersUi(userHost, userQuest)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }

        scoreHostStatus.observe(this@GameActivity) {
            updateScore(scoreHost?.score, scoreQuest?.score)
        }
        scoreQuestStatus.observe(this@GameActivity) {
            updateScore(scoreHost?.score, scoreQuest?.score)
        }

        payloadStatus.observe(this@GameActivity) { result ->
            gameManager.send(result, connectionResult)
        }

        bookStatus.observe(this@GameActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> drawBook(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }

        gameStatus.observe(this@GameActivity) { status ->
            updateUiByStatus(status)
        }

        wordStatus.observe(this@GameActivity) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateTest(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
    }

    private fun updateTest(data: BaseWordModel) = with(binding) {

        if (adapter?.currentList.isNullOrEmpty()) {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
        }

        adapter?.submitList(data.translatedWords.shuffled()) {
            isLoading = false
            TransitionManager.beginDelayedTransition(root, AutoTransition())

            tvQa.text = data.question.parseAsHtml()
            tvTitle.text = data.originalWord.parseAsHtml()
            tvTitle.isGone = data.originalWord.isBlank()
        }
    }

    private fun initTimer() {
        timer?.let {
            it.cancel()
            timer = null
        }
        timer = object : CountDownTimer(60 * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvTimer.text = formatDateMMSS(millisUntilFinished)
            }

            override fun onFinish() {
                viewModel.finishGame()
            }
        }.start()
    }

    private fun getWinner() {
        Analytics.logEvent(Analytics.Event.END_GAME)
        val scoreHost = viewModel.scoreHost?.score ?: 0
        val scoreQuest = viewModel.scoreQuest?.score ?: 0

        val userScoreHost = "$scoreHost - $scoreQuest"
        val userScoreQuest = "$scoreQuest - $scoreHost"

        val userHost = viewModel.userHost!!
        val userQuest = viewModel.userQuest!!

        if (scoreHost > scoreQuest) {
            showWinner(userHost, userScoreHost)
        } else if (scoreHost < scoreQuest) {
            showWinner(userQuest, userScoreQuest)
        } else {
            showWinner(userHost, userScoreHost)
        }
    }

    private fun drawBook(test: BaseTestModel) = with(binding) {
        cardBook.isGone = false
        tvTitleCard.text = test.title
        tvDescriptionCard.text = test.description
        tvIcon.loadImage(storage.getReferenceFromUrl(test.image)) {
            placeholder(null)
            error(null)
        }
        tvIcon.setColorFilter(test.getPrimaryColor().toColor(), PorterDuff.Mode.SRC_IN)
        image.setBackgroundColor(test.getBackgroundColor().toColor())
    }

    private fun initBtns() = with(binding) {
        ivBack.setOnClickListener { onBackPressed() }
        btnExit.setOnClickListener { onBackPressed() }
        btnExit2.setOnClickListener { onBackPressed() }
        btnExit3.setOnClickListener { onBackPressed() }
        btnNext.setOnClickListener {
            viewModel.sendReady()
        }
    }

    private fun initAdapter() {
        adapter = TestAdapter { correct ->
            isLoading = true
            viewModel.processWord(correct)
            if (!correct) vibrate()
        }
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@GameActivity)
        adapter = this@GameActivity.adapter
        addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean =
                isLoading
        })
    }

    private fun showContent() = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        containerContent.isGone = false
        loadingContent.isGone = true
        loadingWaiting.isGone = true
        containerWinner.isGone = true
    }

    private fun showWaiting() = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        containerContent.isGone = true
        loadingContent.isGone = true
        loadingWaiting.isGone = false
        containerWinner.isGone = true
    }

    private fun showLoading() = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        containerContent.isGone = true
        loadingContent.isGone = false
        loadingWaiting.isGone = true
        containerWinner.isGone = true
    }

    private fun showWinner(user: PayLoadModel.User, score: String) = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        containerContent.isGone = true
        loadingContent.isGone = true
        loadingWaiting.isGone = true
        containerWinner.apply {
            imageViewWinner.loadImage(user.photo)
            tvPriceWaiting2.text = "Гравець ${user.name}\nпереміг з рахунком $score"
            isGone = false
        }
    }

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = {
                finish()
            },
            negativeAction = {}
        )
    }

    override fun clear() {
        connectionManager.disconnect(endPointId)
        connectionManager.stop()
    }
}
