package com.ribsky.lessons.ui

import android.app.Activity.RESULT_OK
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.redmadrobot.lib.sd.LoadingStateDelegate
import com.ribsky.common.base.BaseFragment
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ActionExt.Companion.openWifiSettings
import com.ribsky.common.utils.internet.InternetManager
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.adapter.lessons.header.LessonsHeaderAdapter
import com.ribsky.lessons.adapter.lessons.item.LessonsAdapter
import com.ribsky.lessons.databinding.FragmentLessonsBinding
import com.ribsky.navigation.features.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonsFragment :
    BaseFragment<LessonsNavigation, LessonsViewModel, FragmentLessonsBinding>(
        FragmentLessonsBinding::inflate
    ) {
    override val viewModel: LessonsViewModel by viewModel()

    override val navigation: LessonsNavigation by inject()
    private val shopNavigation: ShopNavigation by inject()
    private val dialogsNavigation: DialogsNavigation by inject()
    private val lessonNavigation: LessonNavigation by inject()
    private val betaNavigation: BetaNavigation by inject()

    private var adapter: ConcatAdapter? = null
    private var adapterHeader: LessonsHeaderAdapter? = null
    private var adapterItem: LessonsAdapter? = null
    private var state: LoadingStateDelegate? = null

    private val args: LessonsFragmentArgs by navArgs()
    private val paragraphId by lazy { args.paragraphId }
    private val internetManager: InternetManager by inject()

    private val lessonListener =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val id = it.data?.getStringExtra(LessonNavigation.RESULT_KEY_LESSON_ID)!!
                viewModel.updateLesson(id)
                viewModel.getLessons(paragraphId)
                navigation.navigateSuccess()
            }
        }

    override fun initView() {
        initState()
        initAdapter()
        initRecycler()
    }

    private fun initState() = with(binding) {
        state = LoadingStateDelegate(recyclerView, circularProgressIndicator).apply {
            TransitionManager.beginDelayedTransition(root, AutoTransition())
            showLoading()
        }
    }

    private fun initAdapter() {
        adapterHeader = LessonsHeaderAdapter {
            navigation.navController?.navigateUp()
        }
        adapterItem = LessonsAdapter { model ->
            processLessonClick(model)
        }
        adapter = ConcatAdapter(adapterHeader, adapterItem)
    }

    private fun initRecycler() = with(binding.recyclerView) {
        layoutManager = LinearLayoutManager(activity)
        adapter = this@LessonsFragment.adapter
    }

    override fun initObs() = with(viewModel) {
        viewModel.getLessons(paragraphId)
        lessonsStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> adapterItem?.submitList(result.data)
                Resource.Status.ERROR -> {}
            }
        }
        paragraphStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.LOADING -> showLoading()
                Resource.Status.SUCCESS -> {
                    adapterHeader?.submitList(listOf(result.data)) {
                        TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                        state?.showContent()
                    }
                }
                Resource.Status.ERROR -> {
                }
            }
        }
        setFragmentResultListener(LessonsNavigation.RESULT_KEY_LESSON_INFO) { _, bundle ->
            val lessonId = bundle.getString(LessonsNavigation.RESULT_KEY_LESSON_INFO_ID)!!
            navigation.navigateLesson(lessonNavigation, lessonId, lessonListener)
        }
        setFragmentResultListener(ShopNavigation.RESULT_KEY_PROMPT_SUB) { _, _ ->
            navigation.navigateShop(shopNavigation)
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_PROGRESS) { _, _ ->
            navigation.navigateBeta(betaNavigation)
        }
        setFragmentResultListener(DialogsNavigation.RESULT_KEY_CONNECTION) { _, _ ->
            openWifiSettings()
        }
    }

    private fun showLoading() = with(binding) {
        TransitionManager.beginDelayedTransition(root, AutoTransition())
        state?.showLoading()
    }

    private fun processLessonClick(model: BaseLessonModel) {
        if (internetManager.isOnline() || viewModel.isFileExists(model.content)) {
            if (!model.isInProgress()) {
                if (model.hasPrem) {
                    if (viewModel.isSub) {
                        navigation.navigateLessonInfoDialog(model.id)
                    } else {
                        navigation.navigatePromptSub(shopNavigation)
                    }
                } else {
                    navigation.navigateLessonInfoDialog(model.id)
                }
            } else {
                navigation.navigateProgress(dialogsNavigation)
            }
        } else {
            navigation.navigateInternetError(dialogsNavigation)
        }
    }

    override fun clear() {
        adapter = null
        adapterHeader = null
        adapterItem = null
        state = null
    }
}
