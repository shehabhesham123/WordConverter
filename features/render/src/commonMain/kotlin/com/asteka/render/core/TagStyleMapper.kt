package com.asteka.render.core

interface TagStyleMapper<out Style:Any> {
    fun getStyleForTag(tagName: String): Style
}