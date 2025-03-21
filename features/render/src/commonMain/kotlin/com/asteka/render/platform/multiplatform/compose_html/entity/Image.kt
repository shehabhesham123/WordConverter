package com.asteka.render.platform.multiplatform.compose_html.entity

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.asteka.interpreter.platform.html.tag.HTMLTag
import java.awt.image.BufferedImage
import java.io.File
import java.nio.file.Paths
import javax.imageio.ImageIO

class Image(imgTag: HTMLTag, private val tempStoredFolderPath: String) {
    val id: String = cnt.toString()
    private val name: String = ""
    val width: Float = imgTag.attributes["width"]?.toFloat() ?: 0f
    val height: Float = imgTag.attributes["height"]?.toFloat() ?: 0f
    private val src: String = imgTag.attributes["src"] ?: ""
    val alt = imgTag.attributes["alt"] ?: "[${name}]"
    val bitmap: ImageBitmap = getImageData()

    init {
        getImageName()
    }

    companion object {
        var cnt = 0
            get() {
                field++
                return field
            }
    }

    private fun getImageName(): String {
        return Paths.get(src).fileName?.toString() ?: ""
    }

    private fun getImageData(): ImageBitmap {
        val imagePath = "$tempStoredFolderPath/$src"
        val imageFile = File(imagePath)
        val bufferedImage: BufferedImage = ImageIO.read(imageFile)
        return bufferedImage.toComposeImageBitmap()
    }
}