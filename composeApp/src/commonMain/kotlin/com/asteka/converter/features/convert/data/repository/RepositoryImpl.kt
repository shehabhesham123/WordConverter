package com.asteka.converter.features.convert.data.repository

import com.asteka.converter.core.failure.Failure
import com.asteka.converter.core.functional.Either
import com.asteka.converter.features.convert.domain.entity.WordDoc
import com.asteka.converter.features.convert.domain.repository.Repository

class RepositoryImpl() : Repository() {
    override fun getWordDocLocally(docPath: String): Either<Failure, WordDoc> {
        return try {
            Either.Right(createDocWord(docPath))
        } catch (e: Exception) {
            Either.Left(Failure.UnknownFailure)
        }
    }

    private fun createDocWord(docPath: String): WordDoc {
        return WordDoc(docPath)
    }

}