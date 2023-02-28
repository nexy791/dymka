package com.ribsky.tests.model

import android.os.Parcelable
import com.ribsky.domain.model.test.BaseTestModel
import kotlinx.parcelize.Parcelize

@Parcelize
class TestModel(
    override val id: String,
    override val sort: Int,
    override val title: String,
    override val description: String,
    override val image: String,
    override val content: String,
    override val hasPrem: Boolean,
    override val colors: Map<String, String>,
    var isActive: Boolean,
) : BaseTestModel, Parcelable {

    constructor(
        testModel: BaseTestModel,
        isActive: Boolean,
    ) : this(
        testModel.id,
        testModel.sort,
        testModel.title,
        testModel.description,
        testModel.image,
        testModel.content,
        testModel.hasPrem,
        testModel.colors,
        isActive
    )
}
