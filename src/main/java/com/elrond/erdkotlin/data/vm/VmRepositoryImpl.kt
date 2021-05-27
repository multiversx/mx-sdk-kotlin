package com.elrond.erdkotlin.data.vm

import com.elrond.erdkotlin.data.api.ElrondProxy
import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.domain.vm.QueryContractInput
import com.elrond.erdkotlin.domain.vm.QueryContractOutput
import com.elrond.erdkotlin.domain.vm.VmRepository

internal class VmRepositoryImpl(private val elrondProxy: ElrondProxy) : VmRepository {

    override fun queryContract(queryContractInput: QueryContractInput): QueryContractOutput {
        return requireNotNull(elrondProxy.queryContract(queryContractInput).data).data.toDomain()
    }

}
