package com.elrond.erdkotlin.domain.esdt

class GetAllIssuedEsdtUsecase internal constructor(private val esdtRepository: EsdtRepository) {

    fun execute() = esdtRepository.getAllEsdtIssued()

}
