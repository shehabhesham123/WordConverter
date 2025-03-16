package com.asteka.interpreter.platform.html.interpreter

import com.asteka.interpreter.platform.html.tag.HTMLTag
import org.jsoup.nodes.Element

class HeadInterpreter {
    fun get(head:Element): HTMLTag {
        return HTMLTag("", mapOf(), mutableListOf(),"")
    }
}