package com.example.ImageGeneration

import java.awt.Color
import java.awt.Font
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.util.*
import javax.imageio.ImageIO


fun generateImage(text: String, imageWidth : Int, imageHeight : Int): ByteArray? {
    val image = BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB)

    val imageGraphics = image.createGraphics()
    imageGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
    imageGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
    imageGraphics.color = Color.white
    imageGraphics.fillRect(0, 0, imageWidth, imageHeight)
    imageGraphics.font = Font("Serif", Font.PLAIN, 26)
    imageGraphics.color = Color.blue

    val random = Random()
    var currHeight = 25
    var currWidthOffset = 10
    for (i in text.indices) {
        currWidthOffset += 21
        if (text[i] == '\n') {
            currHeight += 30
            currWidthOffset = 10
        }
        imageGraphics.color = Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))
        imageGraphics.drawString(text[i].toString(), currWidthOffset, (Math.random() * 10 + currHeight).toInt())
    }

    imageGraphics.color = Color.white
    imageGraphics.dispose()
    val bout = ByteArrayOutputStream()
    ImageIO.write(image, "png", bout)

    return bout.toByteArray()
}