package pipeline

fun convertToPipelineMode(pipeline: String): PipelineMode =
    when (pipeline) {
        "sync" -> PipelineMode.Sync
        "async" -> PipelineMode.Async
        else -> throw IllegalArgumentException("Incorrect pipeline mode")
    }
