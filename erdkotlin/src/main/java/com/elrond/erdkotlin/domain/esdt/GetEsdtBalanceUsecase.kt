package com.elrond.erdkotlin.domain.esdt

import com.elrond.erdkotlin.domain.wallet.models.Address

class GetEsdtBalanceUsecase internal constructor(private val esdtRepository: EsdtRepository) {

    fun execute(address: Address, tokenIdentifier: String) = esdtRepository.getEsdtBalance(
        address,
        tokenIdentifier
    )

}
