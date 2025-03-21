package com.asteka.render.core.extension

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.withStyle
import com.asteka.render.platform.multiplatform.compose_html.entity.Style

fun AnnotatedString.Builder.appendStyledContent(style: Style, content: CharSequence) {
    if (style.paragraphStyle != null) {
        withStyle(style.paragraphStyle) {
            withStyle(style.spanStyle) { append(content) }
        }
    } else {
        withStyle(style.spanStyle) { append(content) }
    }
}