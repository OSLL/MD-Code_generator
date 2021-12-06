package com.example

import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

val IMAGE_FORMAT = "png"
val IMAGE = "$FILENAME$DOT$IMAGE_FORMAT"

fun createImage(text: String): BufferedImage {
    var count = 0
    for (element in text)
        if (element.toString() == CARRIAGE_RETURN) count++
    count++

    val width = 400
    val height = count * 17

    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics()
    g.background = Color.WHITE
    g.clearRect(0, 0, width, height)
    g.color = Color.BLACK
    var h = 10
    val w = 7

    val string = mutableListOf<String>()
    for (i in text.indices) {
        if (text[i].toString() != CARRIAGE_RETURN)
            string.add(text[i].toString())
        if (text[i].toString() == CARRIAGE_RETURN) {
            string.add(text[i].toString())
            g.drawString(string.joinToString(""), w, h)
            string.clear()
            h += 17
        }
    }
    g.drawString(BRACE_, w, h)
    return image
}

class Image {
    constructor(text: String) {
        var count = 0
        for (i in 0..(text.length - 1))
            if (text[i].toString() == CARRIAGE_RETURN) count++
        count++

        var width = 400
        var height = count * 17

        var image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        val g = image.createGraphics()
        g.background = Color.WHITE
        g.clearRect(0, 0, width, height)
        g.color = Color.BLACK
        var h = 10
        var w = 7

        val string = mutableListOf<String>()
        for (i in 0..(text.length - 1)) {
            if (text[i].toString() != CARRIAGE_RETURN)
                string.add(text[i].toString())
            if (text[i].toString() == CARRIAGE_RETURN) {
                string.add(text[i].toString())
                g.drawString(string.joinToString(""), w, h)
                string.clear()
                h += 17
            }
        }
        g.drawString(BRACE_, w, h)

        val outputfile = File(IMAGE);
        ImageIO.write(image, IMAGE_FORMAT, outputfile);
    }
}