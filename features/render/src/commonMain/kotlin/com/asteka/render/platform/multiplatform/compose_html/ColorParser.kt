package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.ui.graphics.Color

object ColorParser {
    fun parse(color: String): Color {
        return when {
            // Named colors
            color.equals("red", ignoreCase = true) -> Color.Red
            color.equals("blue", ignoreCase = true) -> Color.Blue
            color.equals("green", ignoreCase = true) -> Color.Green
            color.equals("black", ignoreCase = true) -> Color.Black
            color.equals("white", ignoreCase = true) -> Color.White
            color.equals("orange", ignoreCase = true) -> Color(0xFFA500)
            color.equals("purple", ignoreCase = true) -> Color(0xA020F0)
            color.equals("gray", ignoreCase = true) -> Color.Gray

            // Add more named colors as needed...

            color.startsWith("#") -> parseHexColor(color)

            color.startsWith("rgb") -> parseRgbColor(color)

            color.startsWith("hsl") -> parseHslColor(color)

            else -> Color.Black
        }
    }

    private fun parseHexColor(hex: String): Color {
        return try {
            val cleanHex = hex.removePrefix("#")
            when (cleanHex.length) {
                6 -> Color("0x$cleanHex".toInt())
                8 -> Color("0x$cleanHex".toInt())
                else -> Color.Black // Invalid hex format
            }
        } catch (e: IllegalArgumentException) {
            Color.Black // Fallback for invalid hex values
        }
    }

    private fun parseRgbColor(rgb: String): Color {
        val regex = """rgba?\((\d+),\s*(\d+),\s*(\d+)(?:,\s*([\d.]+))?\)""".toRegex()
        val matchResult = regex.find(rgb)
        if (matchResult != null) {
            val (r, g, b, a) = matchResult.destructured
            val red = r.toInt().coerceIn(0, 255).toFloat()
            val green = g.toInt().coerceIn(0, 255).toFloat()
            val blue = b.toInt().coerceIn(0, 255).toFloat()
            val alpha = a.toFloatOrNull()?.coerceIn(0f, 1f) ?: 1f
            return Color(red, green, blue, alpha)
        }
        return Color.Black // Fallback for invalid RGB/RGBA values
    }

    private fun parseHslColor(hsl: String): Color {
        val regex = """hsla?\((\d+),\s*(\d+)%,\s*(\d+)%(?:,\s*([\d.]+))?\)""".toRegex()
        val matchResult = regex.find(hsl)
        if (matchResult != null) {
            val (h, s, l, a) = matchResult.destructured
            val hue = h.toFloat().coerceIn(0f, 360f)
            val saturation = s.toFloat().coerceIn(0f, 100f) / 100f
            val lightness = l.toFloat().coerceIn(0f, 100f) / 100f
            val alpha = a.toFloatOrNull()?.coerceIn(0f, 1f) ?: 1f
            return Color.hsl(hue, saturation, lightness, alpha)
        }
        return Color.Black // Fallback for invalid HSL/HSLA values
    }
}