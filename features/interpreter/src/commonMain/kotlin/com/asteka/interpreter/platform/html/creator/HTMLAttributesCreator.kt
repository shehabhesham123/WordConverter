package com.asteka.interpreter.platform.html.creator

import org.jsoup.nodes.Attributes

class HTMLAttributesCreator {
    fun create(attributes: Attributes) =
        attributes.associate { it.key to it.value }
}