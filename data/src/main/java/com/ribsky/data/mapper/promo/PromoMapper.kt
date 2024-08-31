package com.ribsky.data.mapper.promo

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.PromoApiModel
import com.ribsky.domain.model.promo.PromoModel

interface PromoMapper : Mapper<PromoApiModel, PromoModel>

class PromoMapperImpl : PromoMapper {

    override fun map(input: PromoApiModel): PromoModel {
        return PromoModel(
            title = input.title,
            description = input.description,
            background = input.background,
            color = input.color,
            link = input.link,
        )
    }

}