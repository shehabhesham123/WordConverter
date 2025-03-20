package com.asteka.interpreter.core

import java.io.File

interface Interpreter {
    fun interpret(file:File): InterpreterResult
}