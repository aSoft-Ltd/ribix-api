package ribix

fun <T> DetailBox<T>?.toString() = when (this) {
    null -> ""
    else -> "$value"
}