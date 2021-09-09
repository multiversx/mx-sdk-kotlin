package com.elrond.erdkotlin

import com.elrond.erdkotlin.data.account.AccountRepositoryImpl
import com.elrond.erdkotlin.data.api.ElrondProxy
import com.elrond.erdkotlin.data.esdt.EsdtRepositoryImpl
import com.elrond.erdkotlin.data.networkconfig.NetworkConfigRepositoryImpl
import com.elrond.erdkotlin.data.transaction.TransactionRepositoryImpl
import com.elrond.erdkotlin.data.vm.VmRepositoryImpl
import com.elrond.erdkotlin.domain.account.GetAccountUsecase
import com.elrond.erdkotlin.domain.account.GetAddressBalanceUsecase
import com.elrond.erdkotlin.domain.account.GetAddressNonceUsecase
import com.elrond.erdkotlin.domain.dns.CheckUsernameUsecase
import com.elrond.erdkotlin.domain.dns.ComputeDnsAddressUsecase
import com.elrond.erdkotlin.domain.dns.RegisterDnsUsecase
import com.elrond.erdkotlin.domain.networkconfig.GetNetworkConfigUsecase
import com.elrond.erdkotlin.domain.transaction.*
import com.elrond.erdkotlin.domain.transaction.SignTransactionUsecase
import com.elrond.erdkotlin.domain.dns.GetDnsRegistrationCostUsecase
import com.elrond.erdkotlin.domain.esdt.*
import com.elrond.erdkotlin.domain.esdt.management.*
import com.elrond.erdkotlin.domain.sc.CallContractUsecase
import com.elrond.erdkotlin.domain.vm.query.QueryContractUsecase
import com.elrond.erdkotlin.domain.vm.query.hex.QueryContractHexUsecase
import com.elrond.erdkotlin.domain.vm.query.integer.QueryContractIntUsecase
import com.elrond.erdkotlin.domain.vm.query.string.QueryContractStringUsecase
import okhttp3.OkHttpClient

// Implemented as an `object` because we are not using any dependency injection library
// We don't want to force the host app to use a specific library.
object ErdSdk {

    fun setNetwork(elrondNetwork: ElrondNetwork) {
        elrondProxy.setUrl(elrondNetwork.url)
    }

    fun getAccountUsecase() = GetAccountUsecase(accountRepository)
    fun getAddressNonceUsecase() = GetAddressNonceUsecase(accountRepository)
    fun getAddressBalanceUsecase() = GetAddressBalanceUsecase(accountRepository)
    fun getNetworkConfigUsecase() = GetNetworkConfigUsecase(networkConfigRepository)
    fun sendTransactionUsecase() = SendTransactionUsecase(
        SignTransactionUsecase(),
        transactionRepository
    )

    fun getTransactionsUsecase() = GetAddressTransactionsUsecase(transactionRepository)
    fun getTransactionInfoUsecase() = GetTransactionInfoUsecase(transactionRepository)
    fun getTransactionStatusUsecase() = GetTransactionStatusUsecase(transactionRepository)
    fun estimateCostOfTransactionUsecase() = EstimateCostOfTransactionUsecase(transactionRepository)
    fun queryContractUsecase() = QueryContractUsecase(vmRepository)
    fun queryContractHexUsecase() = QueryContractHexUsecase(vmRepository)
    fun queryContractStringUsecase() = QueryContractStringUsecase(vmRepository)
    fun queryContracInttUsecase() = QueryContractIntUsecase(vmRepository)
    fun callContractUsecase() = CallContractUsecase(sendTransactionUsecase())
    fun getAllEsdtUsecase() = GetAllEsdtUsecase(esdtRepository)
    fun getAllIssuedEsdtUsecase() = GetAllIssuedEsdtUsecase(esdtRepository)
    fun getEsdtBalanceUsecase() = GetEsdtBalanceUsecase(esdtRepository)
    fun getEsdtPropertiesUsecase() = GetEsdtPropertiesUsecase(esdtRepository)
    fun getEsdtSpecialRolesUsecase() = GetEsdtSpecialRolesUsecase(esdtRepository)
    fun getIssueEsdtUsecase() = IssueEsdtUsecase(sendTransactionUsecase())
    fun getBurnEsdtUsecase() = BurnEsdtUsecase(sendTransactionUsecase())
    fun getChangeOwnerEsdtUsecase() = ChangeOwnerEsdtUsecase(sendTransactionUsecase())
    fun getFreezeAccountEsdtUsecase() = FreezeAccountEsdtUsecase(sendTransactionUsecase())
    fun getMintEsdtUsecase() = MintEsdtUsecase(sendTransactionUsecase())
    fun getPauseAccountEsdtUsecase() = PauseAccountEsdtUsecase(sendTransactionUsecase())
    fun getSetSpecialRolesToAccountEsdtUsecase() = SetSpecialRolesEsdtUsecase(sendTransactionUsecase())
    fun getTransferEsdtUsecase() = TransferEsdtUsecase(sendTransactionUsecase())
    fun getUpgradeEsdtUsecase() = UpgradeEsdtUsecase(sendTransactionUsecase())
    fun getWipeAccountEsdtUsecase() = WipeAccountEsdtUsecase(sendTransactionUsecase())
    fun getDnsRegistrationCostUsecase() = GetDnsRegistrationCostUsecase(
        queryContractUsecase(),
        computeDnsAddressUsecase()
    )

    fun registerDnsUsecase() = RegisterDnsUsecase(
        sendTransactionUsecase(),
        computeDnsAddressUsecase(),
        getDnsRegistrationCostUsecase()
    )

    fun checkUsernameUsecase() = CheckUsernameUsecase()

    internal fun computeDnsAddressUsecase() = ComputeDnsAddressUsecase(checkUsernameUsecase())

    val elrondHttpClientBuilder = OkHttpClient.Builder()
    private val elrondProxy = ElrondProxy(ElrondNetwork.TestNet.url, elrondHttpClientBuilder)
    private val networkConfigRepository = NetworkConfigRepositoryImpl(elrondProxy)
    private val accountRepository = AccountRepositoryImpl(elrondProxy)
    private val transactionRepository = TransactionRepositoryImpl(elrondProxy)
    private val vmRepository = VmRepositoryImpl(elrondProxy)
    private val esdtRepository = EsdtRepositoryImpl(elrondProxy, vmRepository)
}

sealed class ElrondNetwork(open val url: String) {
    object MainNet : ElrondNetwork("https://gateway.elrond.com")
    object DevNet : ElrondNetwork("https://devnet-gateway.elrond.com")
    object TestNet : ElrondNetwork("https://testnet-gateway.elrond.com")
    data class Custom(override val url: String) : ElrondNetwork(url)
}
