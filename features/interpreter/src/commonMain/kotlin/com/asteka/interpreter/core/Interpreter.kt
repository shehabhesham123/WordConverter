package com.asteka.interpreter.core

interface Interpreter {
    fun interpret(code: String): InterpreterResult
}