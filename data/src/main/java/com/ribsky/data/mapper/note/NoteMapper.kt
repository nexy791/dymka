package com.ribsky.data.mapper.note

import com.ribsky.data.mapper.Mapper
import com.ribsky.data.model.NoteApiModel
import com.ribsky.domain.model.note.NoteModel

interface NoteMapper : Mapper<NoteApiModel, NoteModel>

class NoteMapperImpl : NoteMapper {
    override fun map(input: NoteApiModel): NoteModel = NoteModel(
        id = input.id,
        text = input.note,
        paragraphId = input.paragraphId,
    )
}
