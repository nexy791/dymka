package com.ribsky.data.model

import com.ribsky.domain.model.user.BaseUserModel
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserApiModel(
    override val name: String = "",
    override val email: String = "",
    override val image: String = "",
    override var score: Int = 0,
    override var lessons: Map<String, String> = HashMap(),
    override var lessonsCount: Int = 0,
    override var saved: Map<String, String> = HashMap(),
    override var version: Long = 0,
    override var hasPrem: Boolean = false,
) : BaseUserModel {

    constructor(user: BaseUserModel) : this(
        name = user.name,
        email = user.email,
        image = user.image,
        score = user.score,
        lessons = user.lessons,
        lessonsCount = user.lessonsCount,
        saved = user.saved,
        version = user.version
    )
}
