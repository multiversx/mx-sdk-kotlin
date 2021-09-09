package com.elrond.erdkotlin.data.esdt.response

import com.elrond.erdkotlin.domain.esdt.models.EsdtTokenBalance

internal data class GetEsdtBalanceResponse(
    val tokenData: EsdtTokenBalance
)
