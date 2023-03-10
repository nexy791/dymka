package com.ribsky.lessons.dialogs.info

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ViewExt.Companion.getInitials
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.databinding.DialogLessonInfoBinding
import com.ribsky.navigation.features.LessonsNavigation.Companion.RESULT_KEY_LESSON_INFO
import com.ribsky.navigation.features.LessonsNavigation.Companion.RESULT_KEY_LESSON_INFO_ID
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonInfoDialog : BaseSheet<DialogLessonInfoBinding>(DialogLessonInfoBinding::inflate) {

    private val args: LessonInfoDialogArgs by navArgs()
    private val lessonId by lazy { args.lessonId }
    private val viewModel: LessonInfoViewModel by viewModel()

    override fun initViews() {
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            setFragmentResult(
                RESULT_KEY_LESSON_INFO,
                bundleOf(RESULT_KEY_LESSON_INFO_ID to lessonId)
            )
            dismiss()
        }
    }

    private fun updateUi(lesson: BaseLessonModel) = with(binding) {
        tvTitle.text = lesson.name
        tvDescription.text = lesson.description
        imageView.avatarInitials = getInitials(lesson.name)
        btnNext.isEnabled = true
    }

    override fun initObserves() = with(viewModel) {
        getLesson(lessonId)
        lessonStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                else -> {}
            }
        }
    }

    override fun clear() {}
}
