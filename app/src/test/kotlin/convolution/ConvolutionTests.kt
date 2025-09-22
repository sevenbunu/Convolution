import convolution.convolution
import convolution.convolutionParallelCols
import convolution.convolutionParallelPixels
import convolution.convolutionParallelRectangles
import convolution.convolutionParallelRows
import convolution.sequentialConvolution
import filters.convertToFilterType
import filters.createFilter
import io.loadImage
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import java.awt.image.BufferedImage
import java.io.File

class ConvolutionTests {
    private fun imageToByteArray(image: BufferedImage): ByteArray {
        val raster = image.raster
        val width = image.width
        val height = image.height
        val pixels = ByteArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                pixels[y * width + x] = raster.getSample(x, y, 0).toByte()
            }
        }
        return pixels
    }

    private fun assertImagesEqual(
        img1: BufferedImage,
        img2: BufferedImage,
    ) {
        assert(img1.width == img2.width)
        assert(img1.height == img2.height)
        val arr1 = imageToByteArray(img1)
        val arr2 = imageToByteArray(img2)
        assertArrayEquals(arr1, arr2)
    }

    @Test
    fun testParallelPixelsMatchesSequential() {
        val input = File("./src/main/resources/70000.png")
        val outputSeq = File("./src/test/resources/70000.png")
        val outputPar = File("./src/test/resources/70000.png")

        convolution("seq", "id", input, outputSeq, 1, 1, 1)
        convolution("par_pixels", "id", input, outputPar, 1, 1, 1)

        val imgSeq = loadImage(outputSeq)
        val imgPar = loadImage(outputPar)
        assertImagesEqual(imgSeq, imgPar)
    }

    @Test
    fun testParallelRowsMatchesSequential() {
        val input = File("./src/main/resources/70000.png")
        val outputSeq = File("./src/test/resources/70000.png")
        val outputPar = File("./src/test/resources/70000.png")

        convolution("seq", "id", input, outputSeq, 1, 1, 1)
        convolution("par_rows", "id", input, outputPar, 1, 1, 1)

        val imgSeq = loadImage(outputSeq)
        val imgParRows = loadImage(outputPar)
        assertImagesEqual(imgSeq, imgParRows)
    }

    @Test
    fun testParallelColsMatchesSequential() {
        val input = File("./src/main/resources/70000.png")
        val outputSeq = File("./src/test/resources/70000.png")
        val outputPar = File("./src/test/resources/70000.png")

        convolution("seq", "id", input, outputSeq, 1, 1, 1)
        convolution("par_cols", "id", input, outputPar, 1, 1, 1)

        val imgSeq = loadImage(outputSeq)
        val imgParRows = loadImage(outputPar)
        assertImagesEqual(imgSeq, imgParRows)
    }

    @Test
    fun testParallelRectanglesMatchesSequential() {
        val input = File("./src/main/resources/70000.png")
        val outputSeq = File("./src/test/resources/70000.png")
        val outputPar = File("./src/test/resources/70000.png")

        convolution("seq", "id", input, outputSeq, 1, 1, 1)
        convolution("par_rects", "id", input, outputPar, 1, 1, 1)

        val imgSeq = loadImage(outputSeq)
        val imgParRows = loadImage(outputPar)
        assertImagesEqual(imgSeq, imgParRows)
    }
}
