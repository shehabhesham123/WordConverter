package com.asteka.interpreter.platform.html.creator

import com.asteka.interpreter.platform.html.tag.HTMLTag
import org.jsoup.nodes.Element

class HTMLTagCreator(private val htmlAttributesCreator: HTMLAttributesCreator) {
    fun create(body: Element, children: MutableList<HTMLTag>, content: String): HTMLTag {
        val tagName = body.tagName()
        val attributes = htmlAttributesCreator.create(body.attributes())
        return HTMLTag(tagName, attributes, children, content)
    }
}