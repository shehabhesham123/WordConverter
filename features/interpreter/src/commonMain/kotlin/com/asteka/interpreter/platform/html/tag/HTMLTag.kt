package com.asteka.interpreter.platform.html.tag

import com.asteka.interpreter.core.Tag

class HTMLTag(
    var tagName: String,
    val attributes: Map<String, String>,
    val children: MutableList<HTMLTag>,
    val content: String
) : Tag() {
    override fun toString(): String {
        val attributesString = attributes.entries.joinToString(" ") { "${it.key}=\"${it.value}\"" }
        // Replace special markers with children
        var combinedContent = content
        children.forEach { child ->
            combinedContent = combinedContent.replaceFirst("{{CHILD}}", child.toString())
        }
        return "<$tagName${if (attributesString.isNotEmpty()) " $attributesString" else ""}>$combinedContent</$tagName>"
    }
}