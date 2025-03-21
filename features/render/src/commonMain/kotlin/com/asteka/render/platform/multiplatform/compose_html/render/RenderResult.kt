package com.asteka.render.platform.multiplatform.compose_html.render

import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.text.AnnotatedString
import com.asteka.render.core.render.RenderResult

data class ComposeRenderResult(
    val annotatedString: AnnotatedString,
    val inlineTextContentMap: Map<String, InlineTextContent>
) : RenderResult() {
}