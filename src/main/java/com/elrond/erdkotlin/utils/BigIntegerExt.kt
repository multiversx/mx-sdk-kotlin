package com.elrond.erdkotlin.utils

import java.math.BigInteger

fun BigInteger.toHex(): String {
    val asHexstring = this.toString(16)
    if (asHexstring.length % 2 == 1){
        return "0$asHexstring"
    }
    return asHexstring
}
