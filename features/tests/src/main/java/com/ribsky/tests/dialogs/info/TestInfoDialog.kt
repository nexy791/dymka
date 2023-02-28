package com.ribsky.tests.dialogs.info

import android.graphics.PorterDuff
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.navigation.features.TestsNavigation.Companion.RESULT_KEY_TEST_INFO
import com.ribsky.navigation.features.TestsNavigation.Companion.RESULT_KEY_TEST_INFO_ID
import com.ribsky.tests.databinding.DialogTestInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class TestInfoDialog : BaseSheet<DialogTestInfoBinding>(DialogTestInfoBinding::inflate) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)
    private val viewModel: TestInfoViewModel by viewModel()

    private val args: TestInfoDialogArgs by navArgs()
    private val testId by lazy { args.testId }

    override fun initViews() {
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            setFragmentResult(RESULT_KEY_TEST_INFO, bundleOf(RESULT_KEY_TEST_INFO_ID to testId))
            dismiss()
        }
    }

    private fun updateUi(test: BaseTestModel) = with(binding) {
        tvTitle.text = test.title
        tvDescription.text = test.description.parseAsHtml()

        tvIcon.load(storage.getReferenceFromUrl(test.image)) {
            placeholder(null)
            error(null)
        }
        tvIcon.setColorFilter(test.getPrimaryColor().toColor(), PorterDuff.Mode.SRC_IN)
        image.setBackgroundColor(test.getBackgroundColor().toColor())
    }

    override fun initObserves() = with(viewModel) {
        getTest(testId)
        testStatus.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> updateUi(result.data!!)
                else -> {}
            }
        }
    }

    override fun clear() {}
}
