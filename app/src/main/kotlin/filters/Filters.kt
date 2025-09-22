package filters

fun createFilter(filerType: FilterType): Filter {
    val filterType =
        when (filerType) {
            is FilterType.ID -> id(filerType.factor, filerType.bias)
            is FilterType.Blur3x3 -> blur3(filerType.factor, filerType.bias)
            is FilterType.Blur5x5 -> blur5(filerType.factor, filerType.bias)
            is FilterType.GaussianBlur3x3 -> gblur3(filerType.factor, filerType.bias)
            is FilterType.GaussianBlur5x5 -> gblur5(filerType.factor, filerType.bias)
            is FilterType.MotionBlur -> motion(filerType.factor, filerType.bias)
            is FilterType.Edges -> edges(filerType.factor, filerType.bias)
            is FilterType.Sharpen -> sharpen(filerType.factor, filerType.bias)
            is FilterType.Emboss -> emboss(filerType.factor, filerType.bias)
        }
    return filterType
}

fun id(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0),
            doubleArrayOf(
                0.0,
                1.0,
                0.0,
            ),
        )
    return Filter(kernel, factor, bias)
}

fun gblur3(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(1.0, 2.0, 1.0),
            doubleArrayOf(2.0, 4.0, 2.0),
            doubleArrayOf(
                1.0,
                2.0,
                1.0,
            ),
        )
    return Filter(kernel, factor, bias)
}

fun gblur5(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(1.0, 4.0, 6.0, 4.0, 1.0),
            doubleArrayOf(4.0, 16.0, 24.0, 16.0, 4.0),
            doubleArrayOf(6.0, 24.0, 36.0, 24.0, 6.0),
            doubleArrayOf(4.0, 16.0, 24.0, 16.0, 4.0),
            doubleArrayOf(1.0, 4.0, 6.0, 4.0, 1.0),
        )
    return Filter(kernel, factor, bias)
}

fun blur3(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(0.0, 2.0, 0.0),
            doubleArrayOf(2.0, 2.0, 2.0),
            doubleArrayOf(
                0.0,
                2.0,
                0.0,
            ),
        )
    return Filter(kernel, factor, bias)
}

fun blur5(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 1.0, 1.0, 0.0),
            doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0),
            doubleArrayOf(0.0, 1.0, 1.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0),
        )
    return Filter(kernel, factor, bias)
}

fun motion(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
            doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0),
        )
    return Filter(kernel, factor, bias)
}

fun edges(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(-1.0, -1.0, -1.0),
            doubleArrayOf(-1.0, 8.0, -1.0),
            doubleArrayOf(-1.0, -1.0, -1.0),
        )
    return Filter(kernel, factor, bias)
}

fun sharpen(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(-1.0, -1.0, -1.0),
            doubleArrayOf(-1.0, 9.0, -1.0),
            doubleArrayOf(-1.0, -1.0, -1.0),
        )
    return Filter(kernel, factor, bias)
}

fun emboss(
    factor: Double,
    bias: Double,
): Filter {
    val kernel =
        arrayOf(
            doubleArrayOf(-1.0, -1.0, 0.0),
            doubleArrayOf(-1.0, 0.0, 1.0),
            doubleArrayOf(0.0, 1.0, 1.0),
        )
    return Filter(kernel, factor, bias)
}
