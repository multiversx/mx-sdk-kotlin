package com.elrond.erdkotlin.domain.vm.query

import com.elrond.erdkotlin.domain.vm.VmRepository
import com.elrond.erdkotlin.domain.wallet.models.Address

class QueryContractUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32,
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContract(payload)
    }
}
