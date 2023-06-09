package com.ribsky.tests.dialogs.info

import android.graphics.PorterDuff
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import coil.load
import com.google.firebase.storage.FirebaseStorage
import com.ribsky.common.base.BaseSheet
import com.ribsky.common.livedata.Resource
import com.ribsky.common.utils.ext.ResourceExt.Companion.toColor
import com.ribsky.dialogs.factory.error.ErrorFactory.Companion.showErrorDialog
import com.ribsky.domain.model.test.BaseTestModel
import com.ribsky.tests.databinding.DialogTestInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent.inject

class TestInfoDialog(
    private val callback: (String) -> Unit,
) : BaseSheet<DialogTestInfoBinding>(DialogTestInfoBinding::inflate) {

    private val storage: FirebaseStorage by inject(FirebaseStorage::class.java)
    private val viewModel: TestInfoViewModel by viewModel()

    private val testId by lazy { arguments?.getString(KEY_TEST_ID)!! }

    override fun initViews() {
        initBtns()
    }

    private fun initBtns() = with(binding) {
        btnCancel.setOnClickListener {
            dismiss()
        }
        btnNext.setOnClickListener {
            callback(testId)
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
                Resource.Status.LOADING -> {}
                Resource.Status.ERROR -> showErrorDialog(result.exception?.localizedMessage) { dismiss() }
            }
        }
    }

    override fun clear() {}

    companion object {

        private const val KEY_TEST_ID = "KEY_TEST_ID"

        fun newInstance(
            testId: String,
            callback: (String) -> Unit,
        ): TestInfoDialog = TestInfoDialog(callback).apply {
            arguments = bundleOf(KEY_TEST_ID to testId)
        }
    }
}
