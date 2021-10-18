package com.elrond.erdkotlin.domain.sc

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import java.math.BigInteger

class CallContractUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase,
) {

    // source:
    // https://github.com/ElrondNetwork/elrond-sdk/blob/576fdc4bc0fa713738d8556600f04e6377c7623f/erdpy/contracts.py#L62
    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        gasLimit: Long,
        contractAddress: Address,
        funcName: String,
        args: List<String> = emptyList(),
        value: BigInteger = BigInteger.ZERO,
    ): Transaction {
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = contractAddress,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            value = value,
            data = args.fold(funcName) { it1, it2 -> it1 + "@${ScUtils.prepareArgument(it2)}" }
        )
        return sendTransactionUsecase.execute(transaction, wallet)
    }

}
