package com.asteka.interpreter.platform.html.interpreter

import com.asteka.interpreter.core.InterpreterResult
import com.asteka.interpreter.platform.html.tag.HTMLTag

class HTMLInterpreterResult(val head: HTMLTag, val body: HTMLTag) : InterpreterResult() {
}