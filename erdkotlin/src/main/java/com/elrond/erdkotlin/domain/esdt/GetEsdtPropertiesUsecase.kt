package com.elrond.erdkotlin.domain.esdt

class GetEsdtPropertiesUsecase internal constructor(private val esdtRepository: EsdtRepository) {

    fun execute(tokenIdentifier: String) = esdtRepository.getEsdtProperties(
        tokenIdentifier
    )

}
