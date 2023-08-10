package com.ribsky.domain.repository

import com.ribsky.domain.model.bio.BaseFromModel
import com.ribsky.domain.model.bio.BaseGoalModel
import com.ribsky.domain.model.bio.BaseLevelModel

interface BioRepository {

    fun getGoalsList(): List<BaseGoalModel>

    fun getLevelsList(): List<BaseLevelModel>

    fun getFromsList(): List<BaseFromModel>

    fun saveGoal(id: Int)

    fun saveLevel(id: Int)

    fun saveFrom(id: Int)

    fun getGoal(): Int?

    fun getLevel(): Int?

    fun getFrom(): Int?

    fun getGoalById(id: Int): BaseGoalModel?

    fun getLevelById(id: Int): BaseLevelModel?

    fun getFromById(id: Int): BaseFromModel?


}