package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.asteka.interpreter.core.Tag
import com.asteka.interpreter.platform.html.tag.HTMLTag
import com.asteka.render.core.Render

class ComposeHTMLRender(
    composeHTMLTagStyleMapper: ComposeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper: ComposeHTMLAttributesStyleMapper
) : Render<AnnotatedString, Style>(
    composeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper
) {

    override fun render(tag: Tag): AnnotatedString {
        val htmlTag = tag as HTMLTag
        val style = getStyle(htmlTag)



        return buildAnnotatedString {
            if (isBlockLevelElement(htmlTag.tagName))
                appendLine()

            var startIdx = 0
            var childIdx = 0

            """\{\{CHILD}}""".toRegex().findAll(htmlTag.content).forEach { match ->
                val (start, end) = match.range.let { it.first to it.last + 1 }

                appendStyledContent(style, htmlTag.content.subSequence(startIdx, start))
                appendStyledContent(style, render(htmlTag.children[childIdx++]))

                startIdx = end
            }
            if (startIdx < htmlTag.content.length) {
                appendStyledContent(style, htmlTag.content.subSequence(startIdx, htmlTag.content.length))
            }

            if (isBlockLevelElement(tag.tagName) || isSelfClosingElement(tag.tagName))
                appendLine()

        }

    }

    private fun AnnotatedString.Builder.appendStyledContent(style: Style, content: CharSequence) {
        if (style.paragraphStyle != null) {
            withStyle(style.paragraphStyle) {
                withStyle(style.spanStyle) { append(content) }
            }
        } else {
            withStyle(style.spanStyle) { append(content) }
        }
    }

    private fun getStyle(tag: HTMLTag): Style {
        val styleForTag = tagStyleMapper.getStyleForTag(tag.tagName)
        val styleForAttributes = attributesStyleMapper.getAttributeStyle(tag.attributes)
        val spanStyle = styleForTag.spanStyle.merge(styleForAttributes.spanStyle)
        val paragraphStyle =
            styleForTag.paragraphStyle?.merge(styleForAttributes.paragraphStyle) ?: styleForAttributes.paragraphStyle
        val style = Style(spanStyle = spanStyle, paragraphStyle = paragraphStyle)
        return style
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