package com.elrond.erdkotlin.domain.vm.query.hex

import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.domain.vm.query.string.QueryContractStringOutput
import com.elrond.erdkotlin.domain.vm.VmRepository
import com.elrond.erdkotlin.domain.wallet.models.Address

class QueryContractHexUsecase internal constructor(
    private val vmRepository: VmRepository
) {

    fun execute(
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        caller: String? = null,
        value: String? = null
    ): QueryContractStringOutput {
        val payload = QueryContractInput(
            scAddress = contractAddress.bech32,
            funcName = funcName,
            args = args,
            caller = caller,
            value = value
        )
        return vmRepository.queryContractHex(payload)
    }
}
