package com.elrond.erdkotlin.data.vm

import com.elrond.erdkotlin.data.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.domain.vm.VmRepository
import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.domain.vm.query.QueryContractOutput
import com.elrond.erdkotlin.domain.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.domain.vm.query.string.QueryContractStringOutput

internal class VmRepositoryImpl(private val elrondProxy: ElrondProxy) : VmRepository {

    override fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput {
        return requireNotNull(elrondProxy.queryContract(queryContractInput).data).data.toDomain()
    }

    override fun queryContractHex(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondProxy.queryContractHex(queryContractInput).data).toDomain()
    }

    override fun queryContractString(queryContractInput: QueryContractInput): QueryContractStringOutput {
        return requireNotNull(elrondProxy.queryContractString(queryContractInput).data).toDomain()
    }

    override fun queryContractInt(queryContractInput: QueryContractInput): QueryContractDigitOutput {
        return requireNotNull(elrondProxy.queryContractInt(queryContractInput).data).toDomain()
    }

}
