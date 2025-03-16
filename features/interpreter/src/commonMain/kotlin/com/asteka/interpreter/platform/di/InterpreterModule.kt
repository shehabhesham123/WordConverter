package com.asteka.interpreter.platform.di

import com.asteka.interpreter.platform.html.interpreter.BodyInterpreter
import com.asteka.interpreter.platform.html.interpreter.HTMLInterpreter
import com.asteka.interpreter.platform.html.interpreter.HeadInterpreter
import com.asteka.interpreter.platform.html.creator.HTMLAttributesCreator
import com.asteka.interpreter.platform.html.creator.HTMLTagCreator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val InterpreterModule = module {
    singleOf(::HTMLAttributesCreator)
    singleOf(::HTMLTagCreator)
    singleOf(::BodyInterpreter)
    singleOf(::HeadInterpreter)
    singleOf(::HTMLInterpreter)
}