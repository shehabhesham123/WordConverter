package com.asteka.converter.features.convert.data.local

import java.io.File

object Local {
    fun read(filePath: String): String {
        return File(filePath).readText()
    }
}