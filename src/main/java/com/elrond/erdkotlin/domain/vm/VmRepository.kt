package com.elrond.erdkotlin.domain.vm

import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.domain.vm.query.QueryContractOutput
import com.elrond.erdkotlin.domain.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.domain.vm.query.string.QueryContractStringOutput

internal interface VmRepository {

    fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput
    fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringOutput
    fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringOutput
    fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitOutput

}
