package filters

import kotlin.require

class Filter(
    val width: Int,
    val height: Int,
    val kernel: Array<DoubleArray>,
    val factor: Double,
    val bias: Double,
) {
    init {
        require(width != 0 && width % 2 != 0)
        require(height != 0 && height % 2 != 0)
        require(width == height)
        require(kernel.size == height)
        for (row in kernel) {
            require(row.size == width)
        }
    }

    constructor(size: Int, factor: Double, bias: Double) : this(
        size,
        size,
        Array(3) { y -> DoubleArray(3) { x -> 0.0 } },
        factor,
        bias,
    )

    constructor(kernel: Array<DoubleArray>, factor: Double, bias: Double) : this(
        kernel.size,
        kernel[0].size,
        kernel,
        factor,
        bias,
    )

    operator fun get(
        x: Int,
        y: Int,
    ): Double {
        require(0 <= x && x < kernel.size && 0 <= y && y < kernel[0].size)
        return kernel[y][x]
    }

    operator fun set(
        x: Int,
        y: Int,
        value: Double,
    ) {
        require(0 <= x && x < kernel.size && 0 <= y && y < kernel[0].size)
        kernel[y][x] = value
    }

    val centerX = width / 2
    val centerY = height / 2
}
