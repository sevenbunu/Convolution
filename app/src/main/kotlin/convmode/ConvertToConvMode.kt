package convmode

fun convertToConvMode(mode: String): ConvMode =
    when (mode) {
        "seq" -> ConvMode.Sequential
        "par_pixels" -> ConvMode.ParallelPixels
        "par_rows" -> ConvMode.ParallelRows
        "par_cols" -> ConvMode.ParallelCols
        "par_rects" -> ConvMode.ParallelRectangles
        else -> throw IllegalArgumentException("Incorrect convolution mode")
    }
