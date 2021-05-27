package com.elrond.erdkotlin.domain.vm

import com.elrond.erdkotlin.domain.wallet.models.Address

class QuerySmartContractUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): SmartContractOutput {
        val payload = SmartContractQuery(
            scAddress = contractAddress.bech32(),
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContract(payload)
    }
}
