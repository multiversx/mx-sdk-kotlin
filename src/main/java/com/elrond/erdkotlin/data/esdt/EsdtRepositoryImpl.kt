package com.elrond.erdkotlin.data.esdt

import com.elrond.erdkotlin.data.api.ElrondProxy
import com.elrond.erdkotlin.data.toEsdtProperties
import com.elrond.erdkotlin.data.toSpecialRoles
import com.elrond.erdkotlin.domain.esdt.EsdtRepository
import com.elrond.erdkotlin.domain.esdt.models.EsdtProperties
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRoles
import com.elrond.erdkotlin.domain.esdt.models.EsdtTokenBalance
import com.elrond.erdkotlin.domain.esdt.models.EsdtToken
import com.elrond.erdkotlin.domain.vm.VmRepository
import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.domain.wallet.models.Address
import org.bouncycastle.util.encoders.Hex
import java.nio.charset.StandardCharsets

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
        val arg = String(Hex.encode(tokenIdentifier.toByteArray(StandardCharsets.UTF_8)))
        val response = vmRepository.queryContract(
            QueryContractInput(
                scAddress = ESDT_SC_ADDR,
                funcName = "getTokenProperties",
                args = listOf(arg)
            )
        )
        return response.toEsdtProperties()
    }

    override fun getEsdtSpecialRoles(tokenIdentifier: String): EsdtSpecialRoles? {
        val arg = String(Hex.encode(tokenIdentifier.toByteArray(StandardCharsets.UTF_8)))
        val response = vmRepository.queryContract(
            QueryContractInput(
                scAddress = ESDT_SC_ADDR,
                funcName = "getSpecialRoles",
                args = listOf(arg)
            )
        )
        return response.toSpecialRoles()
    }

    companion object {
        const val ESDT_SC_ADDR = "erd1qqqqqqqqqqqqqqqpqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqzllls8a5w6u"
    }
}
