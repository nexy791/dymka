package com.ribsky.bot.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.text.parseAsHtml
import androidx.core.view.isGone
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialSharedAxis
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.mlkit.common.internal.CommonComponentRegistrar
import com.google.mlkit.common.sdkinternal.MlKitContext
import com.google.mlkit.dynamic.DynamicLoadingRegistrar
import com.google.mlkit.nl.languageid.bundled.internal.ThickLanguageIdRegistrar
import com.google.mlkit.nl.languageid.internal.LanguageIdRegistrar
import com.google.mlkit.nl.smartreply.bundled.internal.ThickSmartReplyRegistrar
import com.google.mlkit.nl.smartreply.internal.SmartReplyRegistrar
import com.google.mlkit.nl.translate.NaturalLanguageTranslateRegistrar
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.bot.adapter.chat.ChatAdapter
import com.ribsky.bot.adapter.reply.ReplyAdapter
import com.ribsky.bot.databinding.ActivityBotBinding
import com.ribsky.bot.di.botDi
import com.ribsky.bot.model.ChatModel
import com.ribsky.common.alias.commonDrawable
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.ActionExt.Companion.sendEmail
import com.ribsky.common.utils.ext.ResourceExt.Companion.drawable
import com.ribsky.common.utils.ext.ViewExt.Companion.copy
import com.ribsky.common.utils.ext.ViewExt.Companion.hideKeyboard
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.base.ListDialog
import com.ribsky.dialogs.factory.bot.BotInfoFactory
import com.ribsky.dialogs.factory.bot.BotLimitFactory
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.message.MessageActionFactory
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.navigation.features.ShopNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.util.*

@SuppressLint("ClickableViewAccessibility")
class BotActivity : BaseActivity<BotViewModel, ActivityBotBinding>(ActivityBotBinding::inflate) {

    override val viewModel: BotViewModel by viewModel()
    private val shopNavigation: ShopNavigation by inject()
    private val shareMessageNavigation: ShareMessageNavigation by inject()

    private var adapter: ChatAdapter? = null
    private var replyAdapter: ReplyAdapter? = null

    private var state: LoadingStateDelegate? = null

    private val activityResultRegistry = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        val results: ArrayList<String> =
            data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) ?: arrayListOf()
        val command = results.getOrNull(0)
        command?.let {
            binding.etName.setText(it)
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
        loadKoinModules(botDi)
        try {
            val registrars = listOf(
                CommonComponentRegistrar(),
                DynamicLoadingRegistrar(),
                NaturalLanguageTranslateRegistrar(),
                SmartReplyRegistrar(),
                ThickLanguageIdRegistrar(),
                ThickSmartReplyRegistrar(),
                LanguageIdRegistrar()
            )
            MlKitContext.initialize(this@BotActivity, registrars)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initView(): Unit = with(binding) {
        Analytics.logEvent(Analytics.Event.BOT_OPEN)
        state = LoadingStateDelegate(frame, btnMainLoading)
        initEditText()
        btnPremium.apply {
            setOnClickListener {
                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_MENU)
                shopNavigation.navigate(
                    this@BotActivity,
                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_MENU)
                )
            }
            setPremium(viewModel.isSub)
        }
        ivBack.setOnClickListener { onBackPressed() }
        ivInfo.setOnClickListener {
            showBottomSheetDialog(
                BotInfoFactory(
                    positiveButtonCallback = {
                        sendEmail(
                            subject = "Питання dymka",
                            text = "",
                        )
                    },
                ).createDialog()
            )
        }
    }

    private val isAudio get() = binding.etName.text.isNullOrBlank()

    private fun initEditText() = with(binding.tilDescription) {
        editText!!.doAfterTextChanged {
            TransitionManager.beginDelayedTransition(
                binding.root,
                MaterialSharedAxis(MaterialSharedAxis.Y, true)
            )
            binding.btnMain.setImageDrawable(
                if (editText!!.text.isBlank()) drawable(com.ribsky.bot.R.drawable.ic_round_mic_none_24)
                else drawable(commonDrawable.ic_round_send_24)
            )
        }

        binding.btnMain.setOnClickListener {
            if (isAudio) {
                startAudio()
            } else {
                tryToSendQuestion()
            }
        }
    }

    private fun tryToSendQuestion() {
        if (viewModel.isBotCanReply) {
            val text = binding.etName.text.toString()
            viewModel.sendQuestion(text)
            clearEditText()
        } else {
            if (viewModel.isSub) {
                Analytics.logEvent(Analytics.Event.BOT_LIMIT_PREM)
            } else {
                Analytics.logEvent(Analytics.Event.BOT_LIMIT_DEFAULT)
            }
            showLimitDialog()
        }
    }

    private fun startAudio() {
        val speechRecognizerIntent =
            Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Можна говорити")
            }
        activityResultRegistry.launch(speechRecognizerIntent)
    }

    private fun showLimitDialog() = showBottomSheetDialog(
        BotLimitFactory(
            positiveButtonCallback = {
                if (!viewModel.isSub) {
                    showBottomSheetDialog(
                        SubPromptFactory {
                            Analytics.logEvent(Analytics.Event.PREMIUM_FROM_BOT)
                            shopNavigation.navigate(
                                this@BotActivity,
                                ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_BOT)
                            )
                        }.createDialog()
                    )
                }
            }
        ).createDialog()
    )

    private fun clearEditText() = with(binding.tilDescription) {
        editText?.setText("")
        hideKeyboard(binding.root)
    }

    override fun initObs() = with(viewModel) {
        getUser()
        userStatus.observe(this@BotActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    initAdapterAndRecycler(result.data!!.image)
                    getBot()
                }

                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { finish() }
            }
        }
        botStatus.observe(this@BotActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    getDefaultChat()
                    getScore()
                    binding.emptyView.isGone = true
                }

                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { finish() }
            }
        }
        chatStatus.observe(this@BotActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {
                    TransitionManager.beginDelayedTransition(
                        binding.root,
                        MaterialSharedAxis(MaterialSharedAxis.Y, false)
                    )
                    state?.showLoading()
                    adapter?.showLoading()
                }

                Resource.Status.SUCCESS -> {
                    TransitionManager.beginDelayedTransition(
                        binding.root,
                        MaterialSharedAxis(MaterialSharedAxis.Y, true)
                    )
                    state?.showContent()
                    adapter?.hideLoading()
                    updateChat(it.data!!)
                }

                Resource.Status.ERROR -> showErrorDialog(it.exception?.localizedMessage) { finish() }
            }
        }
        repliesStatus.observe(this@BotActivity) {
            when (it.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> updateReplies(it.data!!)
                Resource.Status.ERROR -> updateReplies(listOf())
            }
        }
        scoreStatus.observe(this@BotActivity) {
            replyAdapter?.setScore(it)
        }
        syncStatus.observe(this@BotActivity) {
            finish()
        }
    }

    private fun updateReplies(list: List<String>) {
        TransitionManager.beginDelayedTransition(
            binding.root,
            MaterialSharedAxis(MaterialSharedAxis.X, false)
        )
        val updatedList =
            listOf("\uD83D\uDE3A ${viewModel.scoreStatus.value} відповідей") + list.ifEmpty {
                listOf("Як у тебе справи?", "Що таке …", "Знайди помилку в …", "Правила …")
            }
        replyAdapter?.submitList(updatedList)
    }

    private fun updateChat(item: List<ChatModel>) {
        adapter?.submitList(item) {
            playSound(commonRaw.sound_message)
            binding.recyclerView.smoothScrollToPosition(item.size - 1)
        }
    }

    private fun initAdapterAndRecycler(photo: String) {
        initAdapter(photo)
        initRecycler()
    }

    private fun initAdapter(photo: String) {
        adapter = ChatAdapter(
            photo,
            object : ChatAdapter.OnChatClickListener {

                override fun onTextClick(text: String) {
                    showSmsDialog(text)
                }
            }
        )
        replyAdapter = ReplyAdapter(object : ReplyAdapter.OnReplyClickListener {
            override fun onReplyClick(text: String) {
                Analytics.logEvent(Analytics.Event.BOT_HINT_CLICK)
                binding.etName.setText(text)
            }

            override fun onLimitClick() {
                showLimitDialog()
            }
        })
    }

    private fun showSmsDialog(text: String) {
        val text = text.parseAsHtml().toString()
        showBottomSheetDialog(
            MessageActionFactory(
                listOf(
                    ListDialog.Item("\uD83D\uDCE3 Поділитися") {
                        shareMessageNavigation.navigate(this, ShareMessageNavigation.Params(text))
                    },
                    ListDialog.Item("\uD83D\uDCDD Скопіювати") {
                        copy(text)
                    },
                    ListDialog.Item("\uD83D\uDC08 Підтримка") {
                        sendEmail(
                            subject = "dymka повідомити про проблему",
                            text = "«$text»"
                        )
                    },
                )
            ).createDialog()
        )
    }

    private fun initRecycler() = with(binding) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@BotActivity)
            adapter = this@BotActivity.adapter
        }
        recyclerViewReplies.apply {
            layoutManager =
                LinearLayoutManager(this@BotActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = this@BotActivity.replyAdapter
        }
        updateReplies(listOf())
    }

    override fun onBackPressed() {
        viewModel.sync()
    }

    override fun clear() {
        adapter = null
        replyAdapter = null
    }
}
