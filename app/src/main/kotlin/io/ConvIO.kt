package io

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun loadImage(input: File): BufferedImage = ImageIO.read(input)

fun saveImage(
    name: File,
    format: String,
    outputImage: BufferedImage,
) {
    ImageIO.write(outputImage, format, name)
}
