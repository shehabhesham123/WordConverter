package com.asteka.render.platform.multiplatform.compose_html.utilities

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException

object Reader {
    fun readImageData(imagePath: String): ByteArray? {
        return try {
            val file = File(imagePath)
            val bytes = ByteArray(file.length().toInt())
            BufferedInputStream(FileInputStream(file)).use { stream ->
                stream.read(bytes)
            }
            bytes
        } catch (e: IOException) {
            println("Error reading the file: ${e.message}")
            null
        }
    }
}
