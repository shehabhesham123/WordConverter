package com.asteka.converter.features.convert.domain.entity

import java.io.File

data class WordDoc(private val path: String) {
    val file = File(path)
}