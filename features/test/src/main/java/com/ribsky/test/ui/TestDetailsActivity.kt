package com.ribsky.test.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.view.MotionEvent
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColorState
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.ext.ViewExt.Companion.snackbar
import com.ribsky.common.utils.ext.ViewExt.Companion.vibrate
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.domain.model.word.BaseWordModel
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.navigation.features.TestNavigation
import com.ribsky.test.adapter.test.TestAdapter
import com.ribsky.test.databinding.ActivityTestDetailsBinding
import com.ribsky.test.model.WordModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class TestDetailsActivity :
    BaseActivity<TestDetailsViewModel, ActivityTestDetailsBinding>(
        ActivityTestDetailsBinding::inflate
    ) {

    override val viewModel: TestDetailsViewModel by viewModel()
    private val shopNavigation: ShopNavigation by inject()

    private var state: LoadingStateDelegate? = null
    private val testId by lazy { intent.getStringExtra(TestNavigation.KEY_TEST_ID)!! }

    private val storage: FirebaseStorage by KoinJavaComponent.inject(FirebaseStorage::class.java)

    private var mAdapter: TestAdapter? = null
    private var isLoading = false

    @SuppressLint("ClickableViewAccessibility")
    override fun initView() {
        Analytics.logEvent(Analytics.Event.START_WORDS)
        initState()
        initAdapter()
        initRecyclerView()
        initBtns()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(container, circularProgressBar).apply {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            showLoading()
        }
    }

    private fun initAdapter() {
        mAdapter = TestAdapter { isCorrect, position ->
            isLoading = true
            if (isCorrect) {
                playSound(commonRaw.sound_success)
                Analytics.logEvent(Analytics.Event.WORDS_ANSWER_CORRECT)
                viewModel.addScore()
            } else {
                playSound(commonRaw.sound_error)
                Analytics.logEvent(Analytics.Event.WORDS_ANSWER_INCORRECT)
                vibrate()
            }
            mAdapter?.showAll(position)
        }
    }

    private fun initRecyclerView() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(this@TestDetailsActivity)
        adapter = mAdapter
        addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean =
                isLoading
        })
    }

    private fun initBtns() = with(binding) {
        btnNext.setOnClickListener {
            viewModel.generateNewWord()
        }
        ivBack.setOnClickListener { onBackPressed() }

        btnPremium.apply {
            setOnClickListener {
                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_MENU)
                shopNavigation.navigate(
                    this@TestDetailsActivity,
                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_MENU)
                )
            }
            setPremium(viewModel.isSub)
        }
        fabLike.setOnClickListener { likeTest() }
    }

    private fun likeTest() {
        if (!viewModel.isSub) {
            showBottomSheetDialog(SubPromptFactory {
                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_LIKE)
                shopNavigation.navigate(
                    this@TestDetailsActivity,
                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_LIKE)
                )
            }.createDialog())
        } else {
            val isSaved = viewModel.toggleWord()
            updateLikeButton(isSaved)
            if (isSaved) showSnackbar("Слово додано до обраних")
            else showSnackbar("Слово видалено з обраних")
        }
    }

    private fun showSnackbar(message: String) = with(binding) {
        snackbar(title = "Обрані", message = message).apply {
            anchorView = cardTest
            show()
        }
    }

    @SuppressLint("Recycle")
    override fun initObs() = with(viewModel) {
        getProfile()
        userStatus.observe(this@TestDetailsActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }

                Resource.Status.SUCCESS -> getTestInfo(testId)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
        testStatus.observe(this@TestDetailsActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }

                Resource.Status.SUCCESS -> {
                    val test = result.data!!
                    getContent(test)
                    updateUi(test)
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
        wordStatus.observe(this@TestDetailsActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                    state?.showLoading()
                }

                Resource.Status.SUCCESS -> updateList(result.data!!)
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
    }

    private fun updateList(data: WordModel) = with(binding) {
        if (mAdapter?.currentList.isNullOrEmpty()) {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
        }
        mAdapter?.submitList(data.translatedWords.shuffled()) {
            isLoading = false
            state?.showContent()
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            updateTitle(data)
            updateLikeButton(data.isSaved)
        }
    }

    private fun updateLikeButton(isSaved: Boolean) = with(binding.fabLike) {
        if (isSaved) {
            setImageResource(commonDrawable.ic_round_favorite_24)
            backgroundTintList = "#64B5F6".toColorState()
            imageTintList = "#FFFFFF".toColorState()
        } else {
            setImageResource(commonDrawable.ic_round_favorite_border_24)
            backgroundTintList = "#FFFFFF".toColorState()
            imageTintList = "#64B5F6".toColorState()
        }
    }

    private fun updateTitle(data: BaseWordModel) = with(binding) {
        tvSmallTitle.text = data.question.parseAsHtml()
        tvTitle.apply {
            text = data.originalWord.parseAsHtml()
            isGone = data.originalWord.isBlank()
        }
    }

    private fun updateUi(data: BaseTestModel) = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        cardTest.isGone = false
        tvTitleCard.text = data.title
        tvDescriptionCard.text = data.description
        tvIcon.load(storage.getReferenceFromUrl(data.image)) {
            placeholder(null)
            error(null)
        }
        tvIcon.setColorFilter(
            data.getPrimaryColor().toColor(),
            PorterDuff.Mode.SRC_IN
        )
        image.setBackgroundColor(data.getBackgroundColor().toColor())
    }

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = {
                Analytics.logEvent(Analytics.Event.END_WORDS)
                finishWithResult()
            },
            negativeAction = { }
        )
    }

    private fun finishWithResult() {
        setResult(Activity.RESULT_OK, Intent().apply {
            putExtra(TestNavigation.KEY_TEST_RESULT, viewModel.getScore())
        })
        finish()
    }

    override fun clear() {
        state = null
        mAdapter = null
    }
}
