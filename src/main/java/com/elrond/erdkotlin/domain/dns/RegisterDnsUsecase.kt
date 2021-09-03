package com.elrond.erdkotlin.domain.dns

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.toHexBytes

class RegisterDnsUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase,
    private val computeDnsAddressUsecase: ComputeDnsAddressUsecase,
    private val getRegistrationCostUsecase: GetDnsRegistrationCostUsecase
) {

    fun execute(
        username: String,
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        gasLimit: Long
    ): Transaction {
        val dnsAddress = computeDnsAddressUsecase.execute(username)
        val encodedName = username.toHexBytes()
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = dnsAddress,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            value = getRegistrationCostUsecase.execute(dnsAddress),
            data = "register@${String(encodedName)}"
        )
        return sendTransactionUsecase.execute(transaction, wallet)
    }

}
