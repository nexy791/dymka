package com.ribsky.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.ribsky.data.mapper.goal.GoalMapper
import com.ribsky.data.mapper.level.LevelMapper
import com.ribsky.data.model.GoalApiModel
import com.ribsky.data.model.LevelApiModel
import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.model.bio.BaseLevelModel
import com.ribsky.domain.repository.BioRepository

class BioRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val levelMapper: LevelMapper,
    private val goalMapper: GoalMapper,
) : BioRepository {


    override fun getGoalsList(): List<BaseGoalModel> =
        goalsList.map { goalMapper.map(it) }

    override fun getLevelsList(): List<BaseLevelModel> =
        levelsList.map { levelMapper.map(it) }

    override fun saveGoal(id: Int) {
        sharedPreferences.edit { putInt(PREF_GOAL, id) }
    }

    override fun saveLevel(id: Int) {
        sharedPreferences.edit { putInt(PREF_LEVEL, id) }
    }

    override fun getGoal(): Int? {
        val goal = sharedPreferences.getInt(PREF_GOAL, -1)
        return if (goal == -1) null else goal
    }

    override fun getLevel(): Int? {
        val level = sharedPreferences.getInt(PREF_LEVEL, -1)
        return if (level == -1) null else level
    }

    override fun getGoalById(id: Int): BaseGoalModel? =
        goalsList.firstOrNull { it.id == id }?.let { goalMapper.map(it) }

    override fun getLevelById(id: Int): BaseLevelModel? =
        levelsList.firstOrNull { it.id == id }?.let { levelMapper.map(it) }

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

    companion object {
        private const val PREF_GOAL = "goal"
        private const val PREF_LEVEL = "level"
    }
}