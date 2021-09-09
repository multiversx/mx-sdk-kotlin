package com.elrond.erdkotlin.domain.esdt

import com.elrond.erdkotlin.domain.wallet.models.Address

class GetAllEsdtUsecase internal constructor(private val esdtRepository: EsdtRepository) {

    fun execute(address: Address) = esdtRepository.getEsdtTokens(address)

}
