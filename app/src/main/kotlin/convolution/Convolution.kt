package convolution

import convmode.ConvMode
import convmode.convertToConvMode
import filters.Filter
import filters.convertToFilterType
import filters.createFilter
import io.loadImage
import io.saveImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.awt.image.BufferedImage
import java.io.File

fun convolution(
    mode: String,
    filter: String,
    input: File,
    output: File,
    batchSize: Int,
    rectHeight: Int,
    rectWidth: Int,
) {
    val filterType =
        try {
            convertToFilterType(filter)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    val convFilter = createFilter(filterType)
    val inputImage =
        try {
            loadImage(input)
        } catch (_: Error) {
            throw IllegalArgumentException("Image name is incorrect")
        }

    val convMode = convertToConvMode(mode)
    when (convMode) {
        ConvMode.Sequential -> {
            sequentialConvolution(inputImage, output, convFilter)
        }

        ConvMode.ParallelPixels -> {
            convolutionParallelPixels(inputImage, output, convFilter)
        }

        ConvMode.ParallelCols -> {
            convolutionParallelCols(inputImage, output, convFilter, batchSize)
        }

        ConvMode.ParallelRows -> {
            convolutionParallelRows(inputImage, output, convFilter, batchSize)
        }

        ConvMode.ParallelRectangles -> {
            convolutionParallelRectangles(inputImage, output, convFilter, rectWidth, rectHeight)
        }
    }
}

fun sequentialConvolution(
    inputImage: BufferedImage,
    output: File,
    filter: Filter,
) {
    val width = inputImage.width
    val height = inputImage.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val outputRaster = outputImage.raster

    for (x in 0 until width) {
        for (y in 0 until height) {
            val newPixel: Int = calculatePixel(x, y, inputImage, filter)
            outputRaster.setSample(x, y, 0, newPixel)
        }
    }
    val format =
        if (output.extension == "") {
            "png"
        } else {
            output.extension
        }
    saveImage(output, format, outputImage)
}

fun convolutionParallelPixels(
    inputImage: BufferedImage,
    output: File,
    filter: Filter,
) = runBlocking {
    val width = inputImage.width
    val height = inputImage.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val outputRaster = outputImage.raster

    for (x in 0 until width) {
        for (y in 0 until height) {
            launch(Dispatchers.Default) {
                val newPixel: Int = calculatePixel(x, y, inputImage, filter)
                outputRaster.setSample(x, y, 0, newPixel)
            }
        }
    }
    val format =
        if (output.extension == "") {
            "png"
        } else {
            output.extension
        }
    saveImage(output, format, outputImage)
}

fun convolutionParallelRows(
    inputImage: BufferedImage,
    output: File,
    filter: Filter,
    batchSize: Int,
) = runBlocking {
    val width = inputImage.width
    val height = inputImage.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val outputRaster = outputImage.raster
    for (batch in 0 until (height + batchSize - 1) / batchSize) {
        val startRow = batch * batchSize
        val finalRow = minOf(((batch + 1) * batchSize), height)
        launch(Dispatchers.Default) {
            for (y in startRow until finalRow) {
                for (x in 0 until width) {
                    val newPixel: Int = calculatePixel(x, y, inputImage, filter)
                    outputRaster.setSample(x, y, 0, newPixel)
                }
            }
        }
    }
    val format =
        if (output.extension == "") {
            "png"
        } else {
            output.extension
        }
    saveImage(output, format, outputImage)
}

fun convolutionParallelCols(
    inputImage: BufferedImage,
    output: File,
    filter: Filter,
    batchSize: Int,
) = runBlocking {
    val width = inputImage.width
    val height = inputImage.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val outputRaster = outputImage.raster
    for (batch in 0 until (width + batchSize - 1) / batchSize) {
        val startCol = batch * batchSize
        val finalCol = minOf(((batch + 1) * batchSize), width)
        launch(Dispatchers.Default) {
            for (x in startCol until finalCol) {
                for (y in 0 until height) {
                    val newPixel: Int = calculatePixel(x, y, inputImage, filter)
                    outputRaster.setSample(x, y, 0, newPixel)
                }
            }
        }
    }
    val format =
        if (output.extension == "") {
            "png"
        } else {
            output.extension
        }
    saveImage(output, format, outputImage)
}

fun convolutionParallelRectangles(
    inputImage: BufferedImage,
    output: File,
    filter: Filter,
    rectWidth: Int,
    rectHeight: Int,
) = runBlocking {
    val width = inputImage.width
    val height = inputImage.height
    val outputImage = BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val outputRaster = outputImage.raster
    for (rectX in 0 until (width + rectWidth - 1) / rectWidth) {
        for (rectY in 0 until (height + rectHeight - 1) / rectHeight) {
            val startX = rectX * rectWidth
            val finalX = minOf(((rectX + 1) * rectWidth), width)
            val startY = rectY * rectHeight
            val finalY = minOf(((rectY + 1) * rectHeight), height)
            launch(Dispatchers.Default) {
                for (x in startX until finalX) {
                    for (y in startY until finalY) {
                        val newPixel: Int = calculatePixel(x, y, inputImage, filter)
                        outputRaster.setSample(x, y, 0, newPixel)
                    }
                }
            }
        }
    }
    val format =
        if (output.extension == "") {
            "png"
        } else {
            output.extension
        }
    saveImage(output, format, outputImage)
}
