package com.ribsky.game.model

import com.ribsky.domain.model.user.BaseUserModel
import com.ribsky.game.utils.adapter.Adapters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class PayLoadModel {

    @JsonClass(generateAdapter = true)
    data class User(
        @Json(name = "name") val name: String,
        @Json(name = "photo") val photo: String,
        @Json(name = "type_user") val type_user: String = "user",
    ) : PayLoadModel() {

        constructor(user: BaseUserModel) : this(
            name = user.name,
            photo = user.image,
        )
    }

    @JsonClass(generateAdapter = true)
    data class Score(
        @Json(name = "score") var score: Int,
        @Json(name = "type_score") val type_score: String = "score",
    ) : PayLoadModel() {
        fun addScore(score: Int) {
            this.score += score
        }

        fun minusScore(score: Int) {
            if (this.score == 0) return
            this.score -= score
        }
    }

    @JsonClass(generateAdapter = true)
    data class Config(
        @Json(name = "config") val pickedId: String,
        @Json(name = "type_config") val type_config: String = "config",
    ) : PayLoadModel()

    @JsonClass(generateAdapter = true)
    data class Type(
        @Json(name = "subtype") val type: SubType,
    ) : PayLoadModel() {

        enum class SubType {
            START, READY, FINISH
        }
    }

    companion object {
        fun getPayLoadFromJson(json: String): PayLoadModel? {
            return runCatching { Adapters.score.fromJson(json)!! }.getOrNull()
                ?: runCatching { Adapters.user.fromJson(json)!! }.getOrNull()
                ?: runCatching { Adapters.config.fromJson(json)!! }.getOrNull()
                ?: runCatching { Adapters.type.fromJson(json)!! }.getOrNull()
        }

        fun getJsonFromPayLoad(payload: PayLoadModel): String {
            return when (payload) {
                is User -> Adapters.user.toJson(payload)
                is Score -> Adapters.score.toJson(payload)
                is Config -> Adapters.config.toJson(payload)
                is Type -> Adapters.type.toJson(payload)
            }
        }
    }
}
