package com.elrond.erdkotlin.data.api

import com.elrond.erdkotlin.data.account.responses.GetAccountResponse
import com.elrond.erdkotlin.data.account.responses.GetAddressBalanceResponse
import com.elrond.erdkotlin.data.account.responses.GetAddressNonceResponse
import com.elrond.erdkotlin.data.account.responses.GetAddressTransactionsResponse
import com.elrond.erdkotlin.data.esdt.response.GetAllEsdtResponse
import com.elrond.erdkotlin.data.esdt.response.GetAllIssuedEsdtResponse
import com.elrond.erdkotlin.data.esdt.response.GetEsdtBalanceResponse
import com.elrond.erdkotlin.data.networkconfig.GetNetworkConfigResponse
import com.elrond.erdkotlin.data.transaction.responses.EstimateCostOfTransactionResponse
import com.elrond.erdkotlin.data.transaction.responses.GetTransactionInfoResponse
import com.elrond.erdkotlin.data.transaction.responses.GetTransactionStatusResponse
import com.elrond.erdkotlin.data.transaction.responses.SendTransactionResponse
import com.elrond.erdkotlin.data.vm.responses.QueryContractDigitResponse
import com.elrond.erdkotlin.domain.vm.query.QueryContractInput
import com.elrond.erdkotlin.data.vm.responses.QueryContractResponse
import com.elrond.erdkotlin.data.vm.responses.QueryContractStringResponse
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.google.gson.Gson
import okhttp3.OkHttpClient

internal class ElrondProxy(
    url: String,
    httpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
) {

    private val gson = Gson()
    private val elrondClient = ElrondClient(url, gson, httpClientBuilder)

    fun setUrl(url: String) {
        elrondClient.url = url
    }

    fun getNetworkConfig(): ElrondClient.ResponseBase<GetNetworkConfigResponse> {
        return elrondClient.doGet("network/config")
    }

    /** Addresses **/

    fun getAccount(address: Address): ElrondClient.ResponseBase<GetAccountResponse> {
        return elrondClient.doGet("address/${address.bech32}")
    }

    fun getAddressNonce(address: Address): ElrondClient.ResponseBase<GetAddressNonceResponse> {
        return elrondClient.doGet("address/${address.bech32}/nonce")
    }

    fun getAddressBalance(address: Address): ElrondClient.ResponseBase<GetAddressBalanceResponse> {
        return elrondClient.doGet("address/${address.bech32}/balance")
    }

    fun getAddressTransactions(address: Address): ElrondClient.ResponseBase<GetAddressTransactionsResponse> {
        return elrondClient.doGet("address/${address.bech32}/transactions")
    }

    /** Transactions **/

    fun sendTransaction(transaction: Transaction): ElrondClient.ResponseBase<SendTransactionResponse> {
        val requestJson = transaction.serialize()
        return elrondClient.doPost("transaction/send", requestJson)
    }

    fun estimateCostOfTransaction(transaction: Transaction): ElrondClient.ResponseBase<EstimateCostOfTransactionResponse> {
        return elrondClient.doPost("transaction/cost", transaction.serialize())
    }

    fun getTransactionInfo(txHash: String, sender: Address?, withResults: Boolean): ElrondClient.ResponseBase<GetTransactionInfoResponse> {
        val params = ArgFormatter().apply {
            addArg(sender) { "sender=${it.bech32}" }
            addArg(withResults) { "withResults=true" }
        }
        return elrondClient.doGet("transaction/$txHash$params")
    }

    fun getTransactionStatus(txHash: String, sender: Address?): ElrondClient.ResponseBase<GetTransactionStatusResponse> {
        val senderAddress = when (sender){
            null -> ""
            else -> "?sender=${sender.bech32}"
        }
        return elrondClient.doGet("transaction/$txHash/status$senderAddress")
    }

    /** VM **/

    // Compute Output of Pure Function
    fun queryContract(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractResponse> {
        return elrondClient.doPost("vm-values/query", gson.toJson(queryContractInput))
    }

    fun queryContractHex(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractStringResponse> {
        return elrondClient.doPost("vm-values/hex", gson.toJson(queryContractInput))
    }

    fun queryContractString(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractStringResponse> {
        return elrondClient.doPost("vm-values/string", gson.toJson(queryContractInput))
    }

    fun queryContractInt(queryContractInput: QueryContractInput): ElrondClient.ResponseBase<QueryContractDigitResponse> {
        return elrondClient.doPost("vm-values/int", gson.toJson(queryContractInput))
    }

    /** ESDT **/

    // Get all ESDT tokens for an address
    fun getEsdtTokens(address: Address): ElrondClient.ResponseBase<GetAllEsdtResponse> {
        return elrondClient.doGet("address/${address.bech32}/esdt")
    }

    // Get balance for an address and an ESDT token
    fun getEsdtBalance(address: Address, tokenIdentifier: String): ElrondClient.ResponseBase<GetEsdtBalanceResponse> {
        return elrondClient.doGet("address/${address.bech32}/esdt/$tokenIdentifier")
    }


    // Get all issued ESDT tokens
    fun getAllIssuedEsdt(): ElrondClient.ResponseBase<GetAllIssuedEsdtResponse> {
        return elrondClient.doGet("network/esdts")
    }

    /** Private **/

    private class ArgFormatter {
        private var args = ""

        fun <T> addArg(arg: T?, formatArg: (T) -> String) {
            val prefix = when {
                args.isEmpty() -> "?"
                else -> "&"
            }
            args += when (arg){
                null -> ""
                else -> prefix + formatArg(arg)
            }
        }

        override fun toString(): String {
            return args
        }
    }


}
