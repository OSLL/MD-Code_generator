package com.example

import com.example.*
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

const val IMAGE_FORMAT = "png"
val IMAGE = "$FILENAME$DOT$IMAGE_FORMAT"

class Image(text: String) {
    init {
        val count = text.count { it.toString() == CARRIAGE_RETURN } + 1

        val imageWidth = 400
        val imageHeight = count * 17

        val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB)
        val imageBuffer = image.createGraphics()

        imageBuffer.background = Color.WHITE
        imageBuffer.clearRect(0, 0, imageWidth, imageHeight)
        imageBuffer.color = Color.BLACK

        val currentX = 7
        var currentY = 10

        val stringBuffer = StringBuilder()
        text.forEach {
            stringBuffer.append(it)

            if (it.toString() == CARRIAGE_RETURN) {
                imageBuffer.drawString(stringBuffer.toString(), currentX, currentY)
                stringBuffer.clear()
                currentY += 17
            }
        }

        imageBuffer.drawString(BRACE_, currentX, currentY)
        val outputfile = File(IMAGE)
        ImageIO.write(image, IMAGE_FORMAT, outputfile)
    }
}