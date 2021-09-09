package com.elrond.erdkotlin.utils

import org.bouncycastle.util.encoders.Hex

internal fun ByteArray.toHexString() = String(Hex.encode(this))
