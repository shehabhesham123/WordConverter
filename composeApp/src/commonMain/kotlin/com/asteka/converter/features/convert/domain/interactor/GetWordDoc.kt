package com.asteka.converter.features.convert.domain.interactor

import com.asteka.converter.features.convert.domain.entity.WordDoc
import com.asteka.converter.core.failure.Failure
import com.asteka.converter.core.functional.Either
import com.asteka.converter.core.interact.Usecase
import com.asteka.converter.features.convert.domain.repository.Repository

class GetWordDocLocally(private val repository: Repository) : Usecase<String, WordDoc>() {
    override fun run(param: String): Either<Failure, WordDoc> {
        return repository.getWordDocLocally(docPath = param)
    }
}