package convolution

import filters.Filter
import java.awt.image.BufferedImage

fun calculatePixel(
    x: Int,
    y: Int,
    image: BufferedImage,
    filter: Filter,
): Int {
    var sum = 0.0
    val filterHeight = filter.height
    val filterWidth = filter.width
    for (filterY in 0 until filterHeight) {
        for (filterX in 0 until filterWidth) {
            val imageX = (x - filter.centerX + filterX + image.width) % image.width
            val imageY = (y - filter.centerY + filterY + image.height) % image.height
            sum += image.raster.getSample(imageX, imageY, 0) * filter[filterX, filterY]
        }
    }
    sum = filter.factor * sum + filter.bias
    return sum.coerceIn(0.0, 255.0).toInt()
}
