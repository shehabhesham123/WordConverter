package com.asteka.render.platform.multiplatform.compose_html

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString

data class RenderResult(val annotatedString: AnnotatedString, val inlineTextContentMap: Map<String, InlineTextContent>) {
}