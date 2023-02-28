package com.ribsky.data.mapper.test

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.TestApiModel
import com.ribsky.domain.model.test.TestModel

interface TestMapper : Mapper<TestApiModel, TestModel>

class TestMapperImpl : TestMapper {
    override fun map(input: TestApiModel): TestModel {
        return TestModel(
            id = input.id,
            sort = input.sort,
            title = input.title,
            description = input.description,
            image = input.image,
            content = input.content,
            hasPrem = input.hasPrem,
            colors = input.colors
        )
    }
}
