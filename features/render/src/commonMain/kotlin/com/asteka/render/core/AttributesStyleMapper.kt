package com.asteka.render.core

interface AttributesStyleMapper<out Style : Any> {
    fun getAttributeStyle(attributes: Map<String, String>): Style
}