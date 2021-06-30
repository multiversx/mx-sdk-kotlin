package com.elrond.erdkotlin.domain.esdt

import com.elrond.erdkotlin.domain.wallet.models.Address
import java.math.BigInteger

internal object EsdtConstants {

    const val ESDT_TRANSFER_GAS_LIMIT = 600000L
    val ESDT_TRANSFER_VALUE = BigInteger.ZERO
    val ESDT_SC_ADDR = Address.fromBech32("erd1qqqqqqqqqqqqqqqpqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzllls8a5w6u")

}
