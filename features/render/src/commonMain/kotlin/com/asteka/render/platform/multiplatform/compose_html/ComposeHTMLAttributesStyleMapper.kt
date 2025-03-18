package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.asteka.render.core.AttributesStyleMapper

class ComposeHTMLAttributesStyleMapper : AttributesStyleMapper<SpanStyle> {
    override fun getAttributeStyle(attributes: Map<String, String>): SpanStyle {
        var builder = SpanStyle()
        attributes.forEach { (key, value) ->
            when (key) {
                "class" -> {
                    if (value.contains("highlight")) {
                        builder = builder.merge(SpanStyle(background = Color.Yellow))
                    }
                    if (value.contains("bold")) {
                        builder = builder.merge(SpanStyle(fontWeight = FontWeight.Bold))
                    }
                    if (value.contains("italic")) {
                        builder = builder.merge(SpanStyle(fontStyle = FontStyle.Italic))
                    }
                }

                "style" -> {
                    value.split(";").forEach { style ->
                        try {
                            val (styleKey, styleValue) = style.split(":").map { it.trim() }
                            when (styleKey) {
                                "color" -> builder = builder.merge(SpanStyle(color = parseColor(styleValue)))
                                "background-color" -> builder =
                                    builder.merge(SpanStyle(background = parseColor(styleValue)))

                                "font-size" -> builder = builder.merge(SpanStyle(fontSize = parseFontSize(styleValue)))
                                "font-weight" -> builder =
                                    builder.merge(SpanStyle(fontWeight = parseFontWeight(styleValue)))

                                "font-style" -> builder =
                                    builder.merge(SpanStyle(fontStyle = parseFontStyle(styleValue)))

                                "text-decoration" -> builder = builder.merge(
                                    SpanStyle(
                                        textDecoration = parseTextDecoration(
                                            styleValue
                                        )
                                    )
                                )
//                           /* "text-align" -> builder.merge(SpanStyle(textAlign = parseTextAlign(styleValue)))*/   in ParagraphStyle
                                /*"line-height" -> builder.merge(SpanStyle(lineHeight = parseLineHeight(styleValue)))*/
                                "letter-spacing" -> builder = builder.merge(
                                    SpanStyle(
                                        letterSpacing = parseLetterSpacing(
                                            styleValue
                                        )
                                    )
                                )

                                "text-shadow" -> builder =
                                    builder.merge(SpanStyle(shadow = parseTextShadow(styleValue)))

                                "font-family" -> builder =
                                    builder.merge(SpanStyle(fontFamily = parseFontFamily(styleValue)))
                                // Add more CSS properties as needed
                            }
                        } catch (e: Exception) { }
                    }
                }

                // Handle specific HTML attributes
                "href" -> {
                    // Links are typically styled with underline and blue color
                    builder = builder.merge(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline))
                }

                "src" -> {
                    // Images or other media elements may not directly affect text styling
                }
                // Add more HTML attributes as needed
            }
        }
        return builder
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