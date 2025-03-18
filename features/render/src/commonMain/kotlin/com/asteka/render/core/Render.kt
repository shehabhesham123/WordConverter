package com.asteka.render.core

import androidx.compose.ui.text.SpanStyle
import com.asteka.interpreter.core.Tag
import com.asteka.interpreter.platform.html.tag.HTMLTag

abstract class Render<out Type : Any, out Style : Any>(
    protected val tagStyleMapper: TagStyleMapper<Style>,
    protected val attributesStyleMapper: AttributesStyleMapper<Style>,
) {
    abstract fun render(tag: Tag): Type
}