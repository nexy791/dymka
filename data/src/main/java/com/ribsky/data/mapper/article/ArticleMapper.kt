package com.ribsky.data.mapper.article

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.ArticleApiModel
import com.ribsky.domain.model.article.ArticleModel

interface ArticleMapper : Mapper<ArticleApiModel, ArticleModel>

class ArticleMapperImpl : ArticleMapper {
    override fun map(input: ArticleApiModel): ArticleModel {
        return ArticleModel(
            id = input.id,
            sort = input.sort,
            categories = input.categories,
            name = input.name,
            description = input.description,
            image = input.image,
            content = input.content,
            hasPrem = input.hasPrem,
        )
    }
}