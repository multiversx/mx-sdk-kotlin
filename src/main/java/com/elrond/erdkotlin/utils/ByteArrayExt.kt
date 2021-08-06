package com.elrond.erdkotlin.utils

internal fun ByteArray.toHexString() = joinToString(separator = ""){ "%02X".format((it.toInt() and 0xFF))}
