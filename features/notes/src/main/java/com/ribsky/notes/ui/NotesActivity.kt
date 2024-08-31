package com.ribsky.notes.ui

import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.LinearLayoutManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.analytics.Analytics
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseActivity
import com.ribsky.common.utils.ext.AlertsExt.Companion.showExitAlert
import com.ribsky.common.utils.ext.ViewExt.Companion.copy
import com.ribsky.common.utils.ext.ViewExt.Companion.showBottomSheetDialog
import com.ribsky.common.utils.sound.SoundHelper
import com.ribsky.core.Resource
import com.ribsky.dialogs.base.ListDialog
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.dialogs.factory.message.MessageActionFactory
import com.ribsky.dialogs.factory.notes.NoteHowToAddFactory
import com.ribsky.dialogs.factory.notes.NoteLimitFactory
import com.ribsky.dialogs.factory.sub.SubPrompt.Companion.navigateSub
import com.ribsky.dialogs.factory.sub.SubPromptFactory
import com.ribsky.domain.model.note.BaseNoteModel
import com.ribsky.navigation.features.NotesNavigation
import com.ribsky.navigation.features.ShareMessageNavigation
import com.ribsky.navigation.features.ShopNavigation
import com.ribsky.notes.adapter.ChatAdapter
import com.ribsky.notes.databinding.ActivityNotesBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesActivity :
    BaseActivity<NotesViewModel, ActivityNotesBinding>(ActivityNotesBinding::inflate) {

    override val viewModel: NotesViewModel by viewModel()
    private val shareMessageNavigation: ShareMessageNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()

    private var state: LoadingStateDelegate? = null

    private var adapter: ChatAdapter? = null

    private val args by lazy { intent.extras!! }

    private val paragraphId by lazy { args.getString(NotesNavigation.KEY_PARAGRAPH_ID)!! }

    override fun initView() {
        Analytics.logEvent(Analytics.Event.NOTES_OPEN)
        initState()
        initBtns()
        initAdapterAndRecycler()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, circularProgressIndicator).apply {
            showLoading()
        }
    }

    private fun initBtns() = with(binding) {
        ivBack.setOnClickListener { onBackPressed() }
        tvLimit.setOnClickListener {
            showBottomSheetDialog(
                NoteLimitFactory(
                    onConfirm = {},
                    onDismiss = {
                        if (!viewModel.isSub) {
                            showBottomSheetDialog(navigateSub(viewModel.discount) {
                                Analytics.logEvent(Analytics.Event.PREMIUM_FROM_NOTES)
                                shopNavigation.navigate(
                                    this@NotesActivity,
                                    ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_NOTES)
                                )
                            })
                        }
                    }
                ).createDialog()
            )
        }
    }

    private fun initAdapter() {
        adapter = ChatAdapter(object : ChatAdapter.OnChatClickListener {
            override fun onTextClick(text: String, id: Int) {
                if (id != -1) {
                    showSmsDialog(text, id)
                }
            }
        }
        )
    }

    private fun initRecycler() = with(binding) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@NotesActivity)
            adapter = this@NotesActivity.adapter
        }
    }

    private fun showSmsDialog(text: String, id: Int) {
        showBottomSheetDialog(
            MessageActionFactory(
                listOf(
                    ListDialog.Item("\uD83D\uDCE3 Поділитися") {
                        shareMessageNavigation.navigate(this, ShareMessageNavigation.Params(text))
                    },
                    ListDialog.Item("\uD83D\uDCDD Видалити") {
                        viewModel.deleteNote(id, paragraphId)
                    },
                    ListDialog.Item("✏️ Скопіювати") {
                        copy(text.parseAsHtml().toString())
                    },
                )
            ).createDialog()
        )
    }

    private fun initAdapterAndRecycler() {
        initAdapter()
        initRecycler()
    }

    override fun initObs() = with(viewModel) {
        getNotesForParagraph(paragraphId)
        status.observe(this@NotesActivity) { result ->
            when (result.status) {
                Resource.Status.LOADING -> state?.showLoading()
                Resource.Status.SUCCESS -> {
                    state?.showContent()
                    updateChat(result.data!!)
                }

                Resource.Status.ERROR -> {
                    state?.showContent()
                    showErrorDialog(result.exception?.localizedMessage)
                }
            }
        }

    }

    private fun updateChat(item: List<BaseNoteModel>) {
        adapter?.submitList(item) {
            SoundHelper.playSound(commonRaw.sound_message)
        }
        val itemsCount = item.filter { it.id != -1 }.size
        if (itemsCount == 0) {
            showBottomSheetDialog(NoteHowToAddFactory().createDialog())
        }
        if (viewModel.isSub) {
            binding.tvLimit.text = "$itemsCount/∞"
        } else {
            binding.tvLimit.text = "$itemsCount/20"
        }
        if (!viewModel.isSub && itemsCount == 20) {
            showBottomSheetDialog(NoteLimitFactory(
                onConfirm = {},
                onDismiss = {
                    showBottomSheetDialog(navigateSub(viewModel.discount) {
                        Analytics.logEvent(Analytics.Event.PREMIUM_FROM_NOTES)
                        shopNavigation.navigate(
                            this@NotesActivity,
                            ShopNavigation.Params(Analytics.Event.PREMIUM_BUY_FROM_NOTES)
                        )
                    })
                }
            ).createDialog())
        }
    }

    override fun onBackPressed() {
        showExitAlert(
            positiveAction = {
                finish()
            },
            negativeAction = { }
        )
    }

    override fun clear() {
        adapter = null
        state = null
    }
}
