enum class PaymentMethod {
    MASTER_CARD, MAESTRO, VISA, MIR, VK_PAY
}
const val VK_PAY_MAX_TRANSFER = 15_000_00
const val VK_PAY_MAX_MONTH_TRANSFERS = 40_000_00
const val MAX_MONTH_TRANSFERS = 600_000_00
const val MAX_TRANSFER = 150_000_00

fun main() {
    val previousTransfers = 10_000_00
    val currentTransfer = 5_000_00
    println(commission(PaymentMethod.MIR, previousTransfers, currentTransfer))
}

fun commission(
    paymentMethod: PaymentMethod = PaymentMethod.VK_PAY,
    previousTransfers: Int = 0,
    currentTransfer: Int
): Int {
    val totalTransfers = previousTransfers + currentTransfer
    if (totalTransfers > MAX_MONTH_TRANSFERS || currentTransfer > MAX_TRANSFER)
        error("Недопустимая операция!")
    return when (paymentMethod) {
        PaymentMethod.VK_PAY -> {
            if (currentTransfer > VK_PAY_MAX_TRANSFER || totalTransfers > VK_PAY_MAX_MONTH_TRANSFERS)
                error("Недопустимая операция")
            else 0
        }
        PaymentMethod.MAESTRO, PaymentMethod.MASTER_CARD -> {
            if (totalTransfers <= 75_000_00) 0
            else ((currentTransfer) * .006 + 20).toInt()
        }
        PaymentMethod.VISA, PaymentMethod.MIR -> {
            if (currentTransfer * .0075 <= 35_00) 35_00
            else (currentTransfer * .0075).toInt()
        }
    }
}