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
    override var hasDiscount: Boolean = false,

    override var botTotalCount: Long = 0,
) : BaseUserModel {

    @JsonClass(generateAdapter = true)
    data class UserApiModelRequest(
        val name: String = "",
        val email: String = "",
        val image: String = "",
        var score: Int = 0,
        var lessons: Map<String, String> = HashMap(),
        var lessonsCount: Int = 0,
        var saved: Map<String, String> = HashMap(),
        var version: Long = 0,
        var hasPrem: Boolean = false,
        var botTotalCount: Long = 0,
    ) {
        constructor(user: BaseUserModel) : this(
            name = user.name,
            email = user.email,
            image = user.image,
            score = user.score,
            lessons = user.lessons,
            lessonsCount = user.lessonsCount,
            saved = user.saved,
            version = user.version,
            hasPrem = user.hasPrem,
            botTotalCount = user.botTotalCount
        )

        fun toMap(): Map<String, Any?> {
            return mapOf(
                "name" to name,
                "email" to email,
                "image" to image,
                "score" to score,
                "lessons" to lessons,
                "lessonsCount" to lessonsCount,
                "saved" to saved,
                "version" to version,
                "hasPrem" to hasPrem,
                "botTotalCount" to botTotalCount,
            )
        }
    }

    constructor(user: BaseUserModel) : this(
        name = user.name,
        email = user.email,
        image = user.image,
        score = user.score,
        lessons = user.lessons,
        lessonsCount = user.lessonsCount,
        saved = user.saved,
        version = user.version,
        hasDiscount = user.hasDiscount,
        hasPrem = user.hasPrem,
    )
}
