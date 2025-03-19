package com.asteka.work_converter

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString

@Composable
fun App(annotatedString: AnnotatedString, inlineTextContentMap: Map<String, InlineTextContent>) {
    val scoll = rememberScrollState()
    Text(annotatedString, inlineContent = inlineTextContentMap, modifier = Modifier.verticalScroll(scoll))
}