package com.asteka.render.core.mapper

interface AttributesStyleMapper<out Style : Any> {
    fun getAttributeStyle(attributes: Map<String, String>): Style
}