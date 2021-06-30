package com.elrond.erdkotlin.domain.esdt.management

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.esdt.EsdtConstants.ESDT_TRANSFER_GAS_LIMIT
import com.elrond.erdkotlin.domain.esdt.EsdtConstants.ESDT_TRANSFER_VALUE
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.sc.ScUtils
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.toHex
import java.math.BigInteger

class TransferEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase
) {

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        receiver: Address,
        tokenIdentifier: String,
        valueToTransfer: BigInteger,
        funcName: String? = null,
        funcArgs: List<String> = emptyList(),
    ): Transaction {
        val args = mutableListOf(
            tokenIdentifier.toHex(),
            valueToTransfer.toHex()
        ).apply {
            if (funcName != null){
                add(funcName.toHex())
            }
            if (funcArgs.isNotEmpty()) {
                addAll(funcArgs.map { ScUtils.prepareArgument(it) })
            }
        }
        return sendTransactionUsecase.execute(
            Transaction(
                sender = account.address,
                receiver = receiver,
                value = ESDT_TRANSFER_VALUE,
                gasLimit = ESDT_TRANSFER_GAS_LIMIT,
                gasPrice = gasPrice,
                data = args.fold("ESDTTransfer") { it1, it2 -> "$it1@$it2" },
                chainID = networkConfig.chainID,
                nonce = account.nonce
            ),
            wallet
        )
    }

}
