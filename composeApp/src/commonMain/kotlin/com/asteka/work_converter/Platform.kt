package com.asteka.work_converter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform