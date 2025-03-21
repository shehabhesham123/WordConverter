package com.asteka.render.core.mapper

interface TagStyleMapper<out Style:Any> {
    fun getStyleForTag(tagName: String): Style
}