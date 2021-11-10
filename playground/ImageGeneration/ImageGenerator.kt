package com.example.ImageGenerator

import com.example.DOT
import com.example.FILENAME

abstract class ImageGenerator(val programText: String) {
    abstract suspend fun generateImage()

    companion object {
        @JvmStatic
        protected val IMAGE_FORMAT = "png"
        val IMAGE_NAME = "$FILENAME$DOT$IMAGE_FORMAT"
    }
}