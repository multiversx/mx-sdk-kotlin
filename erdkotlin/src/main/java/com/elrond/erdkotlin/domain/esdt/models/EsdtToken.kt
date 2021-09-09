package com.elrond.erdkotlin.domain.esdt.models

data class EsdtToken(
    val balance: String,
    val creator: String,
    val hash: String,
    val name: String,
    val nonce: Int,
    val royalties: String,
    val tokenIdentifier: String,
    val uris: List<String>
)
