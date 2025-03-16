package com.asteka.converter.core.functional

sealed class Either<out L, out R> {

    data class Left<out L>
    constructor(val left: L) : Either<L, Nothing>()

    data class Right<out R>
    constructor(val right: R) : Either<Nothing, R>()

    val isRight get() = this is Right<R>

    val isLeft get() = this is Left<L>

    fun <T> fold(fnL: (L) -> T, fnR: (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    suspend fun <T> coFold(fnL: suspend (L) -> T, fnR: suspend (R) -> T): T =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }
}