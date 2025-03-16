package com.asteka.converter.core.failure

sealed class Failure {
    data object ServerFailure : Failure()
    data object NetworkFailure : Failure()
    data object UnknownFailure : Failure()
    abstract class FeatureFailure : Failure()
}