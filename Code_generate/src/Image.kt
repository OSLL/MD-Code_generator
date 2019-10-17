
import com.example.BRACE_
import com.example.CARRIAGE_RETURN
import com.example.END_OF_LINE
import com.example.SEMICOLON
import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import java.awt.BasicStroke
import java.awt.Color
import javax.swing.Spring.height

class Image {
    constructor(text: String) {
        var count = 0
        for (i in 0..(text.length - 1))
            if (text[i].toString() == CARRIAGE_RETURN) count++
        count++
        var image = BufferedImage(400, count * 17, BufferedImage.TYPE_BYTE_GRAY)
        val g = image.getGraphics() as Graphics2D
        g.stroke = BasicStroke(3f)
        g.color = Color.WHITE
//        g.background = Color.WHITE
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

        val outputfile = File("saved.png");
        ImageIO.write(image, "png", outputfile);
    }
}