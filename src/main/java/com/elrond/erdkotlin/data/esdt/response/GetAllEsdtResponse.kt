package com.elrond.erdkotlin.data.esdt.response

import com.elrond.erdkotlin.domain.esdt.models.EsdtToken

internal data class GetAllEsdtResponse(
    val esdts: Map<String /* tokenIdentifier */, EsdtToken>
)
