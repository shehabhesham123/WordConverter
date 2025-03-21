package com.asteka.render.platform.multiplatform

import com.asteka.interpreter.core.Tag
import com.asteka.render.core.render.Render
import com.asteka.render.core.render.RenderResult
import com.asteka.render.platform.multiplatform.compose_html.entity.Style

class ComposeRender(private val renderImpl: Render<RenderResult, Style>) {
    fun render(rootTag: Tag,imageCacheFolderPath:String): RenderResult {
        return renderImpl.render(rootTag,imageCacheFolderPath)
    }
}
