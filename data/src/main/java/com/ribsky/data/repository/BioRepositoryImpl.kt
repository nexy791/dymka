package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.mapper.from.FromMapper
import com.ribsky.data.mapper.goal.GoalMapper
import com.ribsky.data.mapper.level.LevelMapper
import com.ribsky.data.model.FromApiModel
import com.ribsky.data.model.GoalApiModel
import com.ribsky.data.model.LevelApiModel
import com.ribsky.domain.model.bio.BaseFromModel
import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.model.bio.BaseLevelModel
import com.ribsky.domain.repository.BioRepository

class BioRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val levelMapper: LevelMapper,
    private val goalMapper: GoalMapper,
    private val fromMapper: FromMapper,
) : BioRepository {


    override fun getGoalsList(): List<BaseGoalModel> =
        goalsList.map { goalMapper.map(it) }

    override fun getLevelsList(): List<BaseLevelModel> =
        levelsList.map { levelMapper.map(it) }

    override fun getFromsList(): List<BaseFromModel> =
        fromsList.map { fromMapper.map(it) }

    override fun saveGoal(id: Int) {
        sharedPreferences.edit { putInt(PREF_GOAL, id) }
    }

    override fun saveLevel(id: Int) {
        sharedPreferences.edit { putInt(PREF_LEVEL, id) }
    }

    override fun saveFrom(id: Int) {
        sharedPreferences.edit { putInt(PREF_FROM, id) }
    }

    override fun getGoal(): Int? {
        val goal = sharedPreferences.getInt(PREF_GOAL, -1)
        return if (goal == -1) null else goal
    }

    override fun getLevel(): Int? {
        val level = sharedPreferences.getInt(PREF_LEVEL, -1)
        return if (level == -1) null else level
    }

    override fun getFrom(): Int? {
        val from = sharedPreferences.getInt(PREF_FROM, -1)
        return if (from == -1) null else from
    }

    override fun getGoalById(id: Int): BaseGoalModel? =
        goalsList.firstOrNull { it.id == id }?.let { goalMapper.map(it) }

    override fun getLevelById(id: Int): BaseLevelModel? =
        levelsList.firstOrNull { it.id == id }?.let { levelMapper.map(it) }

    override fun getFromById(id: Int): BaseFromModel? =
        fromsList.firstOrNull { it.id == id }?.let { fromMapper.map(it) }

    private val goalsList: List<GoalApiModel> = listOf(
        GoalApiModel(0, "\uD83C\uDDFA\uD83C\uDDE6 Перейти на українську"),
        GoalApiModel(1, "\uD83D\uDCDD Здати ЗНО/ДПА"),
        GoalApiModel(2, "\uD83D\uDCDA Підготуватись до школи"),
        GoalApiModel(3, "\uD83C\uDF89 Підняти рівень знань"),
        GoalApiModel(4, "\uD83D\uDCC8 Для розвитку кар'єри"),
    )

    private val levelsList: List<LevelApiModel> = listOf(
        LevelApiModel(0, "\uD83D\uDE38 Не знаю української"),
        LevelApiModel(1, "\uD83D\uDE40 Знаю трохи"),
        LevelApiModel(2, "\uD83D\uDE3C Знаю добре"),
        LevelApiModel(3, "\uD83D\uDE3B Знаю дуже добре"),
        LevelApiModel(4, "\uD83D\uDE3D Знаю відмінно"),
    )

    private val fromsList: List<FromApiModel> = listOf(
        FromApiModel(0, "\uD83D\uDCF8 З Інсти"),
        FromApiModel(1, "✈️ З Тг"),
        FromApiModel(2, "\uD83D\uDD0D З пошуку"),
        FromApiModel(3, "\uD83D\uDC08 Рекомендація друга"),
        FromApiModel(4, "\uD83C\uDF81 Інше")
    )


    companion object {
        private const val PREF_GOAL = "goal"
        private const val PREF_LEVEL = "level"
        private const val PREF_FROM = "from"
    }
}