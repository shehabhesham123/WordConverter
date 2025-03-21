package com.asteka.render.platform.multiplatform.compose_html.mapper

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.asteka.render.core.mapper.AttributesStyleMapper
import com.asteka.render.platform.multiplatform.compose_html.utilities.ColorParser
import com.asteka.render.platform.multiplatform.compose_html.entity.Style

class ComposeHTMLAttributesStyleMapper : AttributesStyleMapper<Style> {
    override fun getAttributeStyle(attributes: Map<String, String>): Style {
        var spanStyleBuilder = SpanStyle()
        var paragraphStyleBuilder:ParagraphStyle? = null
        attributes.forEach { (key, value) ->
            when (key) {
                "class" -> {
                    if (value.contains("highlight")) {
                        spanStyleBuilder = spanStyleBuilder.merge(SpanStyle(background = Color.Yellow))
                    }
                    if (value.contains("bold")) {
                        spanStyleBuilder = spanStyleBuilder.merge(SpanStyle(fontWeight = FontWeight.Bold))
                    }
                    if (value.contains("italic")) {
                        spanStyleBuilder = spanStyleBuilder.merge(SpanStyle(fontStyle = FontStyle.Italic))
                    }
                }

                "align" ->{
                    println("apply align")
                    paragraphStyleBuilder = if(paragraphStyleBuilder == null) ParagraphStyle(textAlign = parseTextAlign(value))
                    else paragraphStyleBuilder!!.merge(ParagraphStyle(textAlign = parseTextAlign(value)))

                }

                "style" -> {
                    value.split(";").forEach { style ->
                        try {
                            val (styleKey, styleValue) = style.split(":").map { it.trim() }
                            when (styleKey) {
                                "color" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(color = parseColor(styleValue)))

                                "background-color" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(background = parseColor(styleValue)))

                                "font-size" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(fontSize = parseFontSize(styleValue)))

                                "font-weight" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(fontWeight = parseFontWeight(styleValue)))

                                "font-style" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(fontStyle = parseFontStyle(styleValue)))

                                "text-decoration" -> spanStyleBuilder = spanStyleBuilder.merge(
                                    SpanStyle(
                                        textDecoration = parseTextDecoration(
                                            styleValue
                                        )
                                    )
                                )

                                "text-align" ->{
                                    paragraphStyleBuilder = if(paragraphStyleBuilder == null) ParagraphStyle(textAlign = parseTextAlign(styleValue))
                                    else paragraphStyleBuilder!!.merge(ParagraphStyle(textAlign = parseTextAlign(styleValue)))
                                }

                                "line-height" ->{
                                    paragraphStyleBuilder = if(paragraphStyleBuilder == null) ParagraphStyle(lineHeight = parseLineHeight(styleValue))
                                    else paragraphStyleBuilder!!.merge(ParagraphStyle(lineHeight = parseLineHeight(styleValue)))
                                }

                                "letter-spacing" -> spanStyleBuilder = spanStyleBuilder.merge(
                                    SpanStyle(
                                        letterSpacing = parseLetterSpacing(
                                            styleValue
                                        )
                                    )
                                )

                                "text-shadow" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(shadow = parseTextShadow(styleValue)))

                                "font-family" -> spanStyleBuilder =
                                    spanStyleBuilder.merge(SpanStyle(fontFamily = parseFontFamily(styleValue)))
                                // Add more CSS properties as needed
                            }
                        } catch (e: Exception) {
                        }
                    }
                }

                // Handle specific HTML attributes
                "href" -> {
                    // Links are typically styled with underline and blue color
                    spanStyleBuilder =
                        spanStyleBuilder.merge(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline))
                }

                "src" -> {
                    // Images or other media elements may not directly affect text styling
                }
                // Add more HTML attributes as needed
            }
        }
        return Style(spanStyleBuilder, paragraphStyleBuilder)
    }


    private fun parseFontWeight(fontWeightValue: String): FontWeight {
        return when (fontWeightValue) {
            "bold" -> FontWeight.Bold
            "normal" -> FontWeight.Normal
            "100" -> FontWeight.W100
            "200" -> FontWeight.W200
            "300" -> FontWeight.W300
            "400" -> FontWeight.W400
            "500" -> FontWeight.W500
            "600" -> FontWeight.W600
            "700" -> FontWeight.W700
            "800" -> FontWeight.W800
            "900" -> FontWeight.W900
            else -> FontWeight.Normal // Fallback for unknown weights
        }
    }

    private fun parseFontStyle(fontStyleValue: String): FontStyle {
        return when (fontStyleValue) {
            "italic" -> FontStyle.Italic
            "normal" -> FontStyle.Normal
            else -> FontStyle.Normal // Fallback for unknown styles
        }
    }

    private fun parseTextDecoration(textDecorationValue: String): TextDecoration {
        return when (textDecorationValue) {
            "underline" -> TextDecoration.Underline
            "line-through" -> TextDecoration.LineThrough
            "none" -> TextDecoration.None
            else -> TextDecoration.None // Fallback for unknown decorations
        }
    }

    private fun parseTextAlign(textAlignValue: String): TextAlign {
        return when (textAlignValue) {
            "left" -> TextAlign.Left
            "right" -> TextAlign.Right
            "center" -> TextAlign.Center
            "justify" -> TextAlign.Justify
            else -> TextAlign.Left // Fallback for unknown alignments
        }
    }

    private fun parseLineHeight(lineHeightValue: String): TextUnit {
        return lineHeightValue.removeSuffix("px").toInt().sp
    }

    private fun parseLetterSpacing(letterSpacingValue: String): TextUnit {
        return letterSpacingValue.removeSuffix("px").toFloat().sp
    }

    private fun parseTextShadow(textShadowValue: String): Shadow {
        val parts = textShadowValue.split(" ").map { it.trim() }
        val color = parseColor(parts.getOrNull(0) ?: "black")
        val offsetX = parts.getOrNull(1)?.toFloatOrNull() ?: 0f
        val offsetY = parts.getOrNull(2)?.toFloatOrNull() ?: 0f
        val blurRadius = parts.getOrNull(3)?.toFloatOrNull() ?: 0f
        return Shadow(color = color, offset = Offset(offsetX, offsetY), blurRadius = blurRadius)
    }

    private fun parseFontFamily(fontFamilyValue: String): FontFamily {
        return FontFamily.Default // Replace with actual font family parsing logic
    }

    private fun parseColor(colorValue: String): Color {
        return ColorParser.parse(colorValue)
    }

    private fun parseFontSize(fontSizeValue: String): TextUnit {
        return fontSizeValue.removeSuffix("px").toInt().sp
    }
}