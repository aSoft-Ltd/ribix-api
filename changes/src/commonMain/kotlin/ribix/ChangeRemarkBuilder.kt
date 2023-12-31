@file:JsExport
@file:Suppress("NON_EXPORTABLE_TYPE")

package ribix

import kash.Money
import kotlin.js.JsExport
import kotlin.js.JsName

private const val CHANGE_MONEY_REMARK_OF = "changeMoneyRemarkOf"

@JsName(CHANGE_MONEY_REMARK_OF)
fun changeRemarkOf(
    previous: Money,
    current: Money,
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null
): ChangeRemark<Money> {
    if (previous.currency != current.currency) {
        return ChangeRemark.Indeterminate
    }
    val diff = current - previous

    return when {
        previous.amountAsDouble == 0.0 && diff.amountAsDouble < 0 -> ChangeRemark.Decrease(
            pct = Percentage.ONE_HUNDRED,
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        previous.amountAsDouble != 0.0 && diff.amountAsDouble < 0 -> ChangeRemark.Decrease(
            pct = Percentage.fromRatio(diff.amountAsDouble * -1.0 / previous.amountAsDouble),
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        previous.amountAsDouble == 0.0 && diff.amountAsDouble > 0 -> ChangeRemark.Increase(
            pct = Percentage.ONE_HUNDRED,
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        previous.amountAsDouble != 0.0 && diff.amountAsDouble > 0 -> ChangeRemark.Increase(
            pct = Percentage.fromRatio(diff.amountAsDouble / previous.amountAsDouble),
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        else -> ChangeRemark.Fixed(
            at = previous,
            feeling = fixedFeeling ?: ChangeRemark.Fixed.DEFAULT_FEELING
        )
    }
}

fun changeRemarkOf(
    previous: Number,
    current: Number,
    increaseFeeling: ChangeFeeling? = null,
    decreaseFeeling: ChangeFeeling? = null,
    fixedFeeling: ChangeFeeling? = null
): ChangeRemark<Double> {
    val prev = previous.toDouble()
    val diff = current.toDouble() - prev

    return when {
        prev == 0.0 && diff < 0.0 -> ChangeRemark.Decrease(
            pct = Percentage.ONE_HUNDRED,
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        prev != 0.0 && diff < 0.0 -> ChangeRemark.Decrease(
            pct = Percentage.fromRatio(-diff / prev),
            value = diff * -1,
            feeling = decreaseFeeling ?: ChangeRemark.Decrease.DEFAULT_FEELING
        )
        prev == 0.0 && diff > 0.0 -> ChangeRemark.Increase(
            pct = Percentage.ONE_HUNDRED,
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        prev != 0.0 && diff > 0.0 -> ChangeRemark.Increase(
            pct = Percentage.fromRatio(diff / prev),
            value = diff,
            feeling = increaseFeeling ?: ChangeRemark.Increase.DEFAULT_FEELING
        )
        else -> ChangeRemark.Fixed(
            at = prev,
            feeling = fixedFeeling ?: ChangeRemark.Fixed.DEFAULT_FEELING
        )
    }
}