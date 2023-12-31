@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package ribix

import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.js.JsName
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic
import kotlin.math.roundToInt

@Serializable
data class Percentage(
    val asDouble: Double
) {
    @JsName("_ignore_fromNumber")
    constructor(asDouble: Number) : this(asDouble.toDouble())

    companion object {
        @JvmField
        val ZERO = Percentage(0.0)

        @JvmField
        val ONE_HUNDRED = Percentage(100.0)

        @JvmStatic
        fun fromRatio(ratio: Double) = Percentage(ratio * 100)
    }

    val asInt by lazy { asDouble.roundToInt() }

    val asRatio by lazy { asDouble / 100.0 }

    val inRatio by lazy { Ratio(asRatio) }

    fun toRatio() = Ratio(asRatio)
}