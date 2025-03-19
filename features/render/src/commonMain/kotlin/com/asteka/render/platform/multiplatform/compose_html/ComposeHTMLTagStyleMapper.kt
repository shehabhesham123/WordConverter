package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.asteka.render.core.TagStyleMapper

class ComposeHTMLTagStyleMapper : TagStyleMapper<Style> {
    override fun getStyleForTag(tagName: String): Style {
        var paragraphStyle: ParagraphStyle? = null
        val spanStyle = when (tagName.lowercase()) {
            // Block-level elements
            "div" -> SpanStyle() // Default style for div
            "p" -> {
                paragraphStyle = ParagraphStyle(lineHeight = 24.sp)
                SpanStyle(fontSize = 16.sp /*lineHeight = 24 in paragraph style*/)
            } // Paragraph style
            "h1" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 32.sp)
            "h2" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 28.sp)
            "h3" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp)
            "h4" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            "h5" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp)
            "h6" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
            "ul" -> SpanStyle() // Default style for unordered list
            "ol" -> SpanStyle() // Default style for ordered list
            "li" -> SpanStyle() // Default style for list item
            "blockquote" -> SpanStyle(fontStyle = FontStyle.Italic, color = Color.Gray) // Blockquote style
            "pre" -> SpanStyle(fontFamily = FontFamily.Monospace, fontSize = 14.sp) // Preformatted text
            "code" -> SpanStyle(
                fontFamily = FontFamily.Monospace,
                fontSize = 14.sp,
                background = Color.LightGray
            ) // Inline code
            "table" -> SpanStyle() // Default style for table
            "tr" -> SpanStyle() // Default style for table row
            "td" -> SpanStyle(/*padding = TextUnit(4.sp)*/) // Table cell style
            "th" -> SpanStyle(fontWeight = FontWeight.Bold/* padding = TextUnit(4.sp)*/) // Table header style
            "form" -> SpanStyle() // Default style for form
            "input" -> SpanStyle() // Default style for input (not applicable for text rendering)
            "button" -> SpanStyle(background = Color.Blue, color = Color.White) // Button style
            "label" -> SpanStyle(fontWeight = FontWeight.Bold) // Label style
            "textarea" -> SpanStyle() // Default style for textarea (not applicable for text rendering)
            "select" -> SpanStyle() // Default style for select (not applicable for text rendering)
            "option" -> SpanStyle() // Default style for option (not applicable for text rendering)
            "header" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp) // Header style
            "footer" -> SpanStyle(fontSize = 14.sp, color = Color.Gray) // Footer style
            "nav" -> SpanStyle() // Default style for navigation
            "section" -> SpanStyle() // Default style for section
            "article" -> SpanStyle() // Default style for article
            "aside" -> SpanStyle(fontStyle = FontStyle.Italic, color = Color.Gray) // Aside style
            "main" -> SpanStyle() // Default style for main content
            "video" -> SpanStyle() // Default style for video (not applicable for text rendering)
            "audio" -> SpanStyle() // Default style for audio (not applicable for text rendering)
            "source" -> SpanStyle() // Default style for source (not applicable for text rendering)
            "iframe" -> SpanStyle() // Default style for iframe (not applicable for text rendering)
            "canvas" -> SpanStyle() // Default style for canvas (not applicable for text rendering)
            "script" -> SpanStyle() // Default style for script (not applicable for text rendering)
            "style" -> SpanStyle() // Default style for style (not applicable for text rendering)
            "link" -> SpanStyle() // Default style for link (not applicable for text rendering)
            "meta" -> SpanStyle() // Default style for meta (not applicable for text rendering)
            "title" -> SpanStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp) // Title style
            "head" -> SpanStyle() // Default style for head (not applicable for text rendering)
            "body" -> SpanStyle() // Default style for body

            // Inline elements
            "a" -> SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline) // Anchor (link) style
            "strong" -> SpanStyle(fontWeight = FontWeight.Bold) // Strong (bold) style
            "em" -> SpanStyle(fontStyle = FontStyle.Italic) // Emphasis (italic) style
            "b" -> SpanStyle(fontWeight = FontWeight.Bold) // Bold style
            "u" -> SpanStyle(textDecoration = TextDecoration.Underline) // Underline style
            "span" -> SpanStyle() // Default style for span
            "img" -> SpanStyle() // Default style for image (not applicable for text rendering)
            "br" -> SpanStyle() // Default style for line break (not applicable for text rendering)
            "hr" -> SpanStyle(color = Color.Gray) // Horizontal rule style

            // Fallback for unknown tags
            else -> SpanStyle() // Default style for unknown tags
        }
        return Style(spanStyle = spanStyle, paragraphStyle = paragraphStyle)
    }
}