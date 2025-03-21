package com.asteka.render.platform.multiplatform.compose_html.creator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.asteka.render.platform.multiplatform.compose_html.entity.Image

object InlineContentCreator {

    fun getInlineContent(image: Image): InlineTextContent {
        return InlineTextContent(
            placeholder = Placeholder(
                width = TextUnit(image.width, TextUnitType.Sp),
                height = TextUnit(image.height, TextUnitType.Sp),
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center
            ),
            children = {
                Image(
                    bitmap = image.bitmap,
                    contentDescription = image.alt,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.Transparent)
                )
            }
        )
    }
}