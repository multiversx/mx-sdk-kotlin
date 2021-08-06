package com.elrond.erdkotlin.utils

internal fun String.isDigitsOnly(): Boolean {
    val integerChars = '0'..'9'
    return all { it in integerChars }
}
