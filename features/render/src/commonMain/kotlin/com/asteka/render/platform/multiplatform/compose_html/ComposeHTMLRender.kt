package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.asteka.interpreter.core.Tag
import com.asteka.interpreter.platform.html.tag.HTMLTag
import com.asteka.render.core.Render

class ComposeHTMLRender(
    composeHTMLTagStyleMapper: ComposeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper: ComposeHTMLAttributesStyleMapper
) : Render<RenderResult, Style>(
    composeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper
) {

    override fun render(tag: Tag): RenderResult {
        val inlineTextContentMap = mutableMapOf<String, InlineTextContent>()
        val annotatedString = solve(tag, inlineTextContentMap)
        return RenderResult(annotatedString, inlineTextContentMap)
        /*val htmlTag = tag as HTMLTag

        println("tag name : ${tag.tagName}")
        println("tag content : ${tag.content}")
        println("Attributes:")
        tag.attributes.forEach {
            println("key : ${it.key},  value  :  ${it.value}")
        }
        println("children size:  ${tag.children.size}")
        println("--------------------------------------------------")

        if (htmlTag.tagName == "img") {
            val inlineTextContent = getInlineContent(tag)
            return buildAnnotatedString {
                appendInlineContent("1", "[Image]")
            }
        }
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

        }*/
    }

    fun solve(tag: Tag, map: MutableMap<String, InlineTextContent>): AnnotatedString {
        val htmlTag = tag as HTMLTag

        if (htmlTag.tagName == "img") {
            map["1"] = getInlineContent(tag)
            return buildAnnotatedString {
                appendLine()
                appendInlineContent("1", "[Image]")
                appendLine()
            }
        }
        val style = getStyle(htmlTag)
        //val style = Style(SpanStyle(), null)


        return buildAnnotatedString {
            if (isBlockLevelElement(htmlTag.tagName)) {
                //append("\n")
            }


            var startIdx = 0
            var childIdx = 0

            """\{\{CHILD}}""".toRegex().findAll(htmlTag.content).forEach { match ->
                val (start, end) = match.range.let { it.first to it.last + 1 }
                var previousString = htmlTag.content.subSequence(startIdx, start).trim().toString()
                if(previousString.isNotEmpty() ){
                    if(htmlTag.children.isEmpty()) previousString+='\n'
                    appendStyledContent(style,  previousString)
                }
                appendStyledContent(style, solve(htmlTag.children[childIdx++], map))

                startIdx = end
            }
            if (startIdx < htmlTag.content.length) {
                var nextString =  htmlTag.content.subSequence(startIdx, htmlTag.content.length).trim().toString()
                if(nextString.isNotEmpty()){
                    nextString +='\n'
                    appendStyledContent(
                        style,
                        htmlTag.content.subSequence(startIdx, htmlTag.content.length).trim().toString()
                    )
                }
            }

            if (isBlockLevelElement(tag.tagName) || isSelfClosingElement(tag.tagName))
                println("append line : ${tag.tagName}")
            // append("\n")
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

    private fun getInlineContent(tag: HTMLTag): InlineTextContent {
        val attributes = tag.attributes
        val width = attributes["width"]!!.toFloat()
        val height = attributes["height"]!!.toFloat()
        val src = attributes["src"]
        return InlineTextContent(
            placeholder = Placeholder(
                width = TextUnit(width, TextUnitType.Sp),
                height = TextUnit(height, TextUnitType.Sp),
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            ),
            children = {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                )
            }
        )
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