package com.elrond.erdkotlin.domain.esdt.management

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.esdt.EsdtConstants
import com.elrond.erdkotlin.domain.esdt.EsdtConstants.ESDT_MANAGEMENT_GAS_LIMIT
import com.elrond.erdkotlin.domain.esdt.EsdtConstants.ESDT_TRANSACTION_VALUE
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.toHex

class WipeAccountEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase
) {

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        tokenIdentifier: String,
        addressToWipe: Address
    ): Transaction {
        val args = mutableListOf(
            tokenIdentifier.toHex(),
            addressToWipe.hex
        )
        return sendTransactionUsecase.execute(
            Transaction(
                sender = account.address,
                receiver = EsdtConstants.ESDT_SC_ADDR,
                value = ESDT_TRANSACTION_VALUE,
                gasLimit = ESDT_MANAGEMENT_GAS_LIMIT,
                gasPrice = gasPrice,
                data = args.fold("wipe") { it1, it2 -> "$it1@$it2" },
                chainID = networkConfig.chainID,
                nonce = account.nonce
            ),
            wallet
        )
    }

}
