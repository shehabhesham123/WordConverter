package com.asteka.interpreter.platform.html.interpreter

import com.asteka.interpreter.platform.html.creator.HTMLTagCreator
import com.asteka.interpreter.platform.html.tag.HTMLTag
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode

class BodyInterpreter(private val htmlTagCreator: HTMLTagCreator) {

    fun parse(body: Element): HTMLTag {
        val childNodes = body.childNodes()
        val content = StringBuilder()
        val children = mutableListOf<HTMLTag>()

        for (node in childNodes) {
            if (node is Element) {
                content.append("{{CHILD}}")
                children.add(parse(node))
            } else if (node is TextNode) {
                content.append(node.text())
            }
        }
        return htmlTagCreator.create(body, children, content.toString())
    }
}
