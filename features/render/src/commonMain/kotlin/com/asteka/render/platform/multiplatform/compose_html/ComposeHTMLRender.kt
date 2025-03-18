package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.asteka.interpreter.core.Tag
import com.asteka.interpreter.platform.html.tag.HTMLTag
import com.asteka.render.core.Render

class ComposeHTMLRender(
    composeHTMLTagStyleMapper: ComposeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper: ComposeHTMLAttributesStyleMapper
) : Render<AnnotatedString, SpanStyle>(
    composeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper
) {

    override fun render(tag: Tag): AnnotatedString {
        var startIdx = 0
        var childIdx = 0
        (tag as HTMLTag).apply {
            println(content)
            val spanStyle = getStyle(tag)

            return buildAnnotatedString {

                if (isBlockLevelElement(tag.tagName)) {
                    append("\n")
                }

                """\{\{CHILD}}""".toRegex().findAll(content).forEach {
                    val start = it.range.first
                    val end = it.range.last + 1
                    withStyle(spanStyle) {
                        append(content.subSequence(startIdx, start))
                    }
                    withStyle(spanStyle) {
                        append(render(children[childIdx++]))
                    }
                    startIdx = end
                }
                if (startIdx < content.length) {
                    withStyle(spanStyle) {
                        append(content.subSequence(startIdx, content.length))
                    }
                }

                if (isBlockLevelElement(tag.tagName) || isSelfClosingElement(tag.tagName)) {
                    append("\n")
                }

            }
        }
    }

    private fun getStyle(tag: HTMLTag): SpanStyle {
        return attributesStyleMapper.getAttributeStyle(tag.attributes).merge(
        tagStyleMapper.getStyleForTag(tag.tagName))
    }

    private fun isBlockLevelElement(tagName: String): Boolean {
        return when (tagName.lowercase()) {
            "div", "p", "h1", "h2", "h3", "h4", "h5", "h6",
            "ul", "ol", "li", "blockquote", "pre", "table",
            "form", "header", "footer", "section", "article",
            "aside", "nav", "main" -> true
            else -> false
        }
    }

    private fun isSelfClosingElement(tagName: String): Boolean {
        return when (tagName.lowercase()) {
            "br", "hr", "img", "input", "meta", "link" -> true
            else -> false
        }
    }
}