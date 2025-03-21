package com.asteka.render.platform.multiplatform.compose_html.creator

import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import com.asteka.interpreter.platform.html.tag.HTMLTag
import com.asteka.render.core.mapper.AttributesStyleMapper
import com.asteka.render.core.mapper.TagStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.entity.Style

object StyleCreator {

    fun getStyle(
        tag: HTMLTag,
        tagStyleMapper: TagStyleMapper<Style>,
        attributesStyleMapper: AttributesStyleMapper<Style>
    ): Style {
        val styleForTag = tagStyleMapper.getStyleForTag(tag.tagName)
        val styleForAttributes = attributesStyleMapper.getAttributeStyle(tag.attributes)
        val spanStyle = mergeSpanStyles(styleForTag.spanStyle, styleForAttributes.spanStyle)
        val paragraphStyle = mergeParagraphStyles(styleForTag.paragraphStyle, styleForAttributes.paragraphStyle)
        return Style(spanStyle = spanStyle, paragraphStyle = paragraphStyle)
    }

    private fun mergeSpanStyles(vararg styles: SpanStyle): SpanStyle {
        var spanStyle = SpanStyle()
        styles.forEach { spanStyle = spanStyle.merge(it) }
        return spanStyle
    }

    private fun mergeParagraphStyles(vararg paragraphStyles: ParagraphStyle?): ParagraphStyle? {
        var paragraphStyle = ParagraphStyle()
        var isParagraphStyleExist = false
        paragraphStyles.forEach {
            if (it != null) isParagraphStyleExist = true
            paragraphStyle = paragraphStyle.merge(it)
        }
        println(paragraphStyle.textAlign)
        return if (isParagraphStyleExist) paragraphStyle
        else null
    }
}