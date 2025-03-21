package com.asteka.render.platform.multiplatform.compose_html.render

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import com.asteka.interpreter.core.Tag
import com.asteka.interpreter.platform.html.tag.HTMLTag
import com.asteka.render.core.extension.appendStyledContent
import com.asteka.render.core.render.Render
import com.asteka.render.core.render.RenderResult
import com.asteka.render.platform.multiplatform.compose_html.creator.InlineContentCreator
import com.asteka.render.platform.multiplatform.compose_html.creator.StyleCreator
import com.asteka.render.platform.multiplatform.compose_html.entity.Image
import com.asteka.render.platform.multiplatform.compose_html.entity.Style
import com.asteka.render.platform.multiplatform.compose_html.mapper.ComposeHTMLAttributesStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.mapper.ComposeHTMLTagStyleMapper

class ComposeHTMLRender(
    composeHTMLTagStyleMapper: ComposeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper: ComposeHTMLAttributesStyleMapper
) : Render<RenderResult, Style>(
    composeHTMLTagStyleMapper,
    composeHTMLAttributesStyleMapper
) {

    private lateinit var imageCacheFolderPath: String

    override fun render(rootTag: Tag, imageCacheFolderPath: String): RenderResult {
        this.imageCacheFolderPath = imageCacheFolderPath
        val inlineTextContentMap = mutableMapOf<String, InlineTextContent>()  // to add images
        val annotatedString = solve(rootTag, inlineTextContentMap)
        return ComposeRenderResult(annotatedString, inlineTextContentMap)
    }

    private fun solve(tag: Tag, inlineTextContentMap: MutableMap<String, InlineTextContent>): AnnotatedString {
        val htmlTag = tag as HTMLTag
        printAttributes(htmlTag)
        if (htmlTag.tagName == "img") {
            val image = Image(htmlTag, imageCacheFolderPath)

            inlineTextContentMap[image.id] = InlineContentCreator.getInlineContent(image)
            return buildAnnotatedString {
                appendLine()
                appendInlineContent(image.id, image.alt)
                appendLine()
            }
        }

        val style = StyleCreator.getStyle(
            tag = htmlTag,
            tagStyleMapper = super.tagStyleMapper,
            attributesStyleMapper = super.attributesStyleMapper
        )

        return buildAnnotatedString {

            var startIdx = 0
            var childIdx = 0

            """\{\{CHILD}}""".toRegex().findAll(htmlTag.content).forEach { match ->
                val (childStart, childEnd) = match.range.let { it.first to it.last + 1 }

                var previousString = htmlTag.content.subSequence(startIdx, childStart).trim().toString()

                if (previousString.isNotEmpty()) {
                    if (htmlTag.children.isEmpty()) previousString += '\n'
                    appendStyledContent(style, previousString)
                }
                appendStyledContent(style, solve(htmlTag.children[childIdx++], inlineTextContentMap))

                startIdx = childEnd
            }
            if (startIdx < htmlTag.content.length) {
                val nextString = htmlTag.content.subSequence(startIdx, htmlTag.content.length).trim().toString() + '\n'
                if (nextString.isNotEmpty()) {
                    appendStyledContent(
                        style,
                        htmlTag.content.subSequence(startIdx, htmlTag.content.length).trim().toString()
                    )
                }
            }
        }
    }

    private fun printAttributes(htmlTag: HTMLTag) {
        println("tagName: ${htmlTag.tagName}")
        htmlTag.attributes.forEach { t, u ->
            println("key: ${t}, value: ${u}")
        }
    }
}