package pipeline

import convolution.convolution
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

fun startPipeline(
    pipeline: String,
    mode: String,
    filter: String,
    input: File,
    output: File,
    batchSize: Int,
    rectHeight: Int,
    rectWidth: Int,
) {
    if (!output.exists()) {
        output.mkdir()
    }
    val pipelineMode =
        try {
            convertToPipelineMode(pipeline)
        } catch (e: IllegalArgumentException) {
            throw e
        }
    when (pipelineMode) {
        PipelineMode.Sync ->
            runSyncPipeline(
                mode,
                filter,
                input,
                output,
                batchSize,
                rectHeight,
                rectWidth,
            )

        PipelineMode.Async ->
            runAsyncPipeline(
                mode,
                filter,
                input,
                output,
                batchSize,
                rectHeight,
                rectWidth,
            )
    }
}

fun runSyncPipeline(
    mode: String,
    filter: String,
    input: File,
    output: File,
    batchSize: Int,
    rectHeight: Int,
    rectWidth: Int,
) {
    val imageExtensions = setOf("png", "jpg", "jpeg", "bmp", "gif")
    val images =
        input.listFiles()?.filter { file -> file.isFile && file.extension.lowercase() in imageExtensions }
            ?: emptyList()
    for (image in images) {
        convolution(
            mode,
            filter,
            image,
            File(output.path + "/" + image.path.split("/").last()),
            batchSize,
            rectHeight,
            rectWidth,
        )
    }
}

fun runAsyncPipeline(
    mode: String,
    filter: String,
    input: File,
    output: File,
    batchSize: Int,
    rectHeight: Int,
    rectWidth: Int,
) = runBlocking {
    val channel = Channel<File>(capacity = 10)
    launch {
        val imageExtensions = setOf("png", "jpg", "jpeg", "bmp", "gif")
        val images =
            input.listFiles()?.filter { file -> file.isFile && file.extension.lowercase() in imageExtensions }
                ?: emptyList()
        for (image in images) {
            channel.send(image)
        }
        channel.close()
    }
    val concurrency = 8
    val processors =
        List(concurrency) {
            launch {
                for (image in channel) {
                    convolution(
                        mode,
                        filter,
                        image,
                        File(output.path + "/" + image.path.split("/").last()),
                        batchSize,
                        rectHeight,
                        rectWidth,
                    )
                }
            }
        }
    processors.joinAll()
}
