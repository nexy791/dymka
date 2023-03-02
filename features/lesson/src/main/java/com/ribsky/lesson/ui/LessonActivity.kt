package com.ribsky.lesson.ui

import android.content.Intent
import android.util.Log
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.AlertsExt.Companion.showErrorAlert
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ResourceExt.Companion.drawable
import com.ribsky.common.utils.ext.ViewExt.Companion.copy
import com.ribsky.common.utils.ext.ViewExt.Companion.hideKeyboard
import com.ribsky.common.utils.ext.ViewExt.Companion.showKeyboard
import com.ribsky.lesson.adapter.chat.ChatAdapter
import com.ribsky.lesson.databinding.ActivityLessonBinding
import com.ribsky.lesson.model.ChatModel
import com.ribsky.navigation.features.LessonNavigation
import com.ribsky.navigation.features.ShareMessageNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonActivity :
    BaseActivity<LessonNavigation, LessonViewModel, ActivityLessonBinding>(ActivityLessonBinding::inflate) {

    override val viewModel: LessonViewModel by viewModel()
    override val navigation: LessonNavigation by inject()
    private val shareWordNavigation: ShareMessageNavigation by inject()

    private var state: LoadingStateDelegate? = null

    private var adapter: ChatAdapter? = null

    private val args by lazy { intent.extras!! }

    private val lessonId by lazy {
        args.getString(LessonNavigation.KEY_LESSON_ID)!!
    }

    override fun initView() {
        Analytics.logEvent(Analytics.Event.START_LESSON)
        initState()
        initBtns()
        initEditText()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, circularProgressIndicator).apply {
            showLoading()
        }
    }

    private fun initBtns() = with(binding) {
        icHelp.setOnClickListener {
            sendEmail(
                subject = "dymka повідомити про проблему",
                text = "Урок #${lessonId}\n\n"
            )
        }
        btnNext.setOnClickListener {
            disableButton()
            viewModel.skipText()
        }
        ivBack.setOnClickListener { onBackPressed() }
    }

    private fun initEditText() = with(binding.tilDescription) {
        setEndIconOnClickListener {
            val text = editText!!.text.toString()
            viewModel.checkAnswer(text)
            clearEditText()
        }
        editText!!.setOnEditorActionListener { _, _, _ ->
            val text = editText!!.text.toString()
            viewModel.checkAnswer(text)
            clearEditText()
            return@setOnEditorActionListener text.isNotBlank()
        }
        editText!!.doAfterTextChanged {
            endIconDrawable = if (editText!!.text.isBlank()) null
            else drawable(commonDrawable.ic_round_send_24)
        }
    }

    private fun initAdapter(photo: String) {
        adapter = ChatAdapter(
            photo,
            object : ChatAdapter.OnChatClickListener {
                override fun onTextClick(text: String) {
                    showSmsDialog(text)
                }

                override fun onMistakeClick(text: String) {
                    viewModel.checkAnswer(text)
                }

                override fun onTestClick(test: ChatModel.Test.TestModel) {
                    viewModel.checkAnswer(test)
                }

                override fun onChipsClick(chips: List<String>) {
                    viewModel.checkAnswer(chips)
                }
            }
        )
    }

    private fun initRecycler() = with(binding) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@LessonActivity)
            adapter = this@LessonActivity.adapter
        }
    }

    private fun showSmsDialog(text: String) {
        navigation.navigateMessageAction(text)
    }

    private fun initAdapterAndRecycler(photo: String) {
        initAdapter(photo)
        initRecycler()
    }

    override fun initObs() = with(viewModel) {
        getUser()
        userStatus.observe(this@LessonActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    initAdapterAndRecycler(result.data!!.image)
                    getLesson(lessonId)
                }
                Resource.Status.ERROR -> showError(result.exception?.localizedMessage.orEmpty())
            }
        }
        lessonStatus.observe(this@LessonActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> getContent(result.data!!.content)
                Resource.Status.ERROR -> showError(result.exception?.localizedMessage.orEmpty())
            }
        }
        contentStatus.observe(this@LessonActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {
                    disableButton()
                    state?.showLoading()
                }
                Resource.Status.SUCCESS -> state?.showContent()
                Resource.Status.ERROR -> showError(result.exception?.localizedMessage.orEmpty())
            }
        }
        chatStatus.observe(this@LessonActivity) { list ->
            updateChat(list)
        }

        endEvent.observe(this@LessonActivity) {
            if (it) endLesson()
        }

        successEvent.observe(this@LessonActivity) {
            if (it) adapter?.disableActiveLastElement()
        }

        actionStatus.observe(this@LessonActivity) { result ->
            binding.btnNext.text = result
        }

        supportFragmentManager.setFragmentResultListener(
            LessonNavigation.RESULT_KEY_MESSAGE_ACTION,
            this@LessonActivity
        ) { _, bundle ->
            val action =
                bundle.getParcelable<LessonNavigation.ResultAction>(LessonNavigation.RESULT_KEY_MESSAGE_ACTION_ID)!!
            processAction(action)
        }
    }

    private fun processAction(action: LessonNavigation.ResultAction) {
        when (action) {
            is LessonNavigation.ResultAction.COPY -> copy(action.message.parseAsHtml().toString())
            is LessonNavigation.ResultAction.SHARE -> navigation.shareMessage(
                shareWordNavigation,
                action.message
            )
            is LessonNavigation.ResultAction.SUPPORT -> sendEmail(
                subject = "dymka повідомити про проблему ",
                text = "Урок #${lessonId}\n\n«${action.message.parseAsHtml()}»"
            )
        }
    }

    private fun updateChat(item: List<ChatModel>) {
        val element = item.lastOrNull()
        Log.e("TAG111", "updateChat: $element")
        if (element != null) {
            adapter?.submitList(item) {
                updateKeyBoardAndBtnVisibility(element)
                scroll(item.size - 1)
            }
        } else {
            endLesson()
        }
    }

    private fun enableButton() = with(binding) {
        btnNext.isEnabled = true
    }

    private fun disableButton() = with(binding) {
        btnNext.isEnabled = false
    }

    private fun clearEditText() = with(binding.tilDescription) {
        editText?.setText("")
    }

    private fun updateKeyBoardAndBtnVisibility(element: ChatModel) {
        when (element) {
            is ChatModel.TranslateText -> {
                showKeyBoard()
                disableButton()
            }
            is ChatModel.Text, is ChatModel.Image -> {
                showButton()
                enableButton()
            }
            is ChatModel.Chips,
            is ChatModel.Mistake,
            is ChatModel.Test,
            -> {
                showButton()
                disableButton()
            }
            is ChatModel.Answer,
            is ChatModel.TextFromUser,
            -> {
            }
        }
    }

    private fun showKeyBoard() = with(binding) {
        btnNext.isGone = true
        tilDescription.isGone = false
        tilDescription.editText?.requestFocus()
        showKeyboard(root)
    }

    private fun showButton() = with(binding) {
        btnNext.isGone = false
        tilDescription.isGone = true
        root.requestFocus()
        hideKeyboard(binding.root)
    }

    private fun endLesson() {
        setResult(
            RESULT_OK,
            Intent().apply {
                putExtra(LessonNavigation.RESULT_KEY_LESSON_ID, lessonId)
            }
        )
        finish()
    }

    private fun scroll(position: Int) {
        binding.recyclerView.smoothScrollToPosition(position)
    }

    private fun showError(error: String) = showErrorAlert(
        message = error,
        positiveAction = null,
        negativeAction = { finish() }
    )

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = { finish() },
            negativeAction = { }
        )
    }

    override fun clear() {
        adapter = null
        state = null
    }
}
