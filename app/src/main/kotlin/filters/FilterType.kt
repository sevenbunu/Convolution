package filters

sealed class FilterType {
    data class ID(
        val factor: Double = 1.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class Blur3x3(
        val factor: Double = 1.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class Blur5x5(
        val factor: Double = 1.0 / 13.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class GaussianBlur3x3(
        val factor: Double = 1.0 / 16.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class GaussianBlur5x5(
        val factor: Double = 1.0 / 256.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class MotionBlur(
        val factor: Double = 1.0 / 9.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class Edges(
        val factor: Double = 1.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class Sharpen(
        val factor: Double = 1.0,
        val bias: Double = 0.0,
    ) : FilterType()

    data class Emboss(
        val factor: Double = 1.0,
        val bias: Double = 128.0,
    ) : FilterType()
}
