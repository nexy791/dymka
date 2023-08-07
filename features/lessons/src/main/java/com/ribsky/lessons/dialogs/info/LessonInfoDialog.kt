package com.ribsky.lessons.dialogs.info

import androidx.core.os.bundleOf
import com.ribsky.common.alias.commonRaw
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.utils.ext.ViewExt.Companion.getInitials
import com.ribsky.common.utils.sound.SoundHelper.playSound
import com.ribsky.core.Resource
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.lesson.BaseLessonModel
import com.ribsky.lessons.databinding.DialogLessonInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LessonInfoDialog(
    private val callback: ((String) -> Unit)? = null,
) : BaseSheet<DialogLessonInfoBinding>(DialogLessonInfoBinding::inflate) {

    private val lessonId by lazy { requireArguments().getString(KEY_LESSON_ID) }
    private val viewModel: LessonInfoViewModel by viewModel()

    override fun initViews() {
        if (callback == null || lessonId == null) dismiss()
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            playSound(commonRaw.sound_celebration)
            callback?.invoke(lessonId!!)
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
        lessonId?.let { getLesson(it) }
        lessonStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
            }
        }
    }

    override fun clear() {}

    companion object {
        private const val KEY_LESSON_ID = "KEY_LESSON_ID"
        fun newInstance(lessonId: String, callback: (String) -> Unit) =
            LessonInfoDialog(callback).apply {
                arguments = bundleOf(KEY_LESSON_ID to lessonId)
            }
    }
}
