package com.elrond.erdkotlin.domain.esdt

class GetEsdtSpecialRolesUsecase internal constructor(private val esdtRepository: EsdtRepository) {

    fun execute(tokenIdentifier: String) = esdtRepository.getEsdtSpecialRoles(
        tokenIdentifier
    )

}
