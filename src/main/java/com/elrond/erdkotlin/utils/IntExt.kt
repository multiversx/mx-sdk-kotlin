package com.elrond.erdkotlin.utils

fun Int.toHexString() = "%02X".format(this and 0xFF)
