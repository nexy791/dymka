package com.ribsky.domain.model.promo

data class PromoModel(
    override val title: String,
    override val description: String,
    override val background: String,
    override val color: String,
    override val link: String
) : BasePromoModel
