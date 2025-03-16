package com.asteka.converter.features.convert.domain.repository

import com.asteka.converter.features.convert.domain.entity.WordDoc
import com.asteka.converter.core.failure.Failure
import com.asteka.converter.core.functional.Either

abstract class Repository {
    abstract suspend fun getWordDocLocally(docPath:String): Either<Failure, WordDoc>
}