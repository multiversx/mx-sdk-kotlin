package com.elrond.erdkotlin.data.esdt

import com.elrond.erdkotlin.data.api.ElrondProxy
import com.elrond.erdkotlin.data.toEsdtProperties
import com.elrond.erdkotlin.data.toSpecialRoles
import com.elrond.erdkotlin.domain.esdt.EsdtConstants
import com.elrond.erdkotlin.domain.esdt.EsdtRepository
import com.elrond.erdkotlin.domain.esdt.models.EsdtProperties
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRoles
import com.elrond.erdkotlin.domain.esdt.models.EsdtTokenBalance
import com.elrond.erdkotlin.domain.esdt.models.EsdtToken
import com.elrond.erdkotlin.domain.vm.VmRepository
import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.utils.toHex

internal class EsdtRepositoryImpl(
    private val elrondProxy: ElrondProxy,
    private val vmRepository: VmRepository
) : EsdtRepository {

    override fun getEsdtTokens(address: Address): Map<String, EsdtToken> {
        return requireNotNull(elrondProxy.getEsdtTokens(address).data).esdts
    }

    override fun getEsdtBalance(address: Address, tokenIdentifier: String): EsdtTokenBalance {
        return requireNotNull(elrondProxy.getEsdtBalance(address, tokenIdentifier).data).tokenData
    }

    override fun getAllEsdtIssued(): List<String> {
        return requireNotNull(elrondProxy.getAllIssuedEsdt().data).tokens
    }

    override fun getEsdtProperties(tokenIdentifier: String): EsdtProperties {
        val response = vmRepository.queryContract(
            QueryContractInput(
                scAddress = EsdtConstants.ESDT_SC_ADDR.bech32,
                funcName = EsdtConstants.GET_TOKEN_PROPERTIES,
                args = listOf(tokenIdentifier.toHex())
            )
        )
        return response.toEsdtProperties()
    }

    override fun getEsdtSpecialRoles(tokenIdentifier: String): EsdtSpecialRoles? {
        val response = vmRepository.queryContract(
            QueryContractInput(
                scAddress = EsdtConstants.ESDT_SC_ADDR.bech32,
                funcName = EsdtConstants.GET_SPECIAL_ROLES,
                args = listOf(tokenIdentifier.toHex())
            )
        )
        return response.toSpecialRoles()
    }

}
