package benchmarks

import convolution.convolution
import java.io.File
import kotlin.time.measureTime

fun benchmark(
    mode: String,
    input: File,
    output: File,
    filter: String,
    results: File,
    batchSize: Int = 1,
    rectWidth: Int = 1,
    rectHeight: Int = 1,
) {
    results.appendText(mode + "\n")
    repeat(40) {
        val executionTime =
            measureTime {
                convolution(mode, filter, input, output, batchSize, rectWidth, rectHeight)
            }
        results.appendText("$executionTime ")
    }
    results.appendText("\n")
}

fun runBenchmarks(
    input: File,
    output: File,
    filter: String,
    results: File,
) {
    benchmark("seq", input, output, filter, results)
    benchmark("par_pixels", input, output, filter, results)
    benchmark("par_cols", input, output, filter, results)
    benchmark("par_cols", input, output, filter, results, batchSize = 10)
    benchmark("par_cols", input, output, filter, results, batchSize = 100)
    benchmark("par_rows", input, output, filter, results)
    benchmark("par_rows", input, output, filter, results, batchSize = 10)
    benchmark("par_rows", input, output, filter, results, batchSize = 100)
    benchmark("par_rects", input, output, filter, results)
    benchmark("par_rects", input, output, filter, results, rectHeight = 10, rectWidth = 10)
    benchmark("par_rects", input, output, filter, results, rectHeight = 100, rectWidth = 100)
}

fun main() {
    val results = File("result.csv")
    results.writeText("Results\n")
    val path = "app/src/main/resources/"
    val input = File(path + "70000.png")
    val output = File(path + "new_image.png")
    runBenchmarks(input, output, "id", results)
}
