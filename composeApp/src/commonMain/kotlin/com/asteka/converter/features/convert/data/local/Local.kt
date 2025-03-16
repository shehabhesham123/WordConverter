package com.asteka.converter.features.convert.data.local

import java.io.File

class Local {
    fun read(filePath: String): String {
        return File(filePath).readText()
    }
}