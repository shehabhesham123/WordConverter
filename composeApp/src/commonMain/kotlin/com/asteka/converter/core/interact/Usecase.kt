package com.asteka.converter.core.interact

import com.asteka.converter.core.failure.Failure
import com.asteka.converter.core.functional.Either

abstract class Usecase<in Param, out Type : Any> {
    operator fun invoke(param: Param): Either<Failure, Type> {
        return run(param)
    }

    protected abstract fun run(param: Param): Either<Failure, Type>

    // for none param
    class None()
}