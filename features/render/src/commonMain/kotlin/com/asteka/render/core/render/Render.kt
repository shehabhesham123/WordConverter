package com.asteka.render.core.render

import com.asteka.interpreter.core.Tag
import com.asteka.render.core.mapper.AttributesStyleMapper
import com.asteka.render.core.mapper.TagStyleMapper

abstract class Render<out Type : RenderResult, out Style : Any>(
    protected val tagStyleMapper: TagStyleMapper<Style>,
    protected val attributesStyleMapper: AttributesStyleMapper<Style>,
) {
    abstract fun render(rootTag: Tag, imageCacheFolderPath:String): Type
}