@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package ribix

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
data class DetailBox<T>(
    val value: T,
    val details: String
)