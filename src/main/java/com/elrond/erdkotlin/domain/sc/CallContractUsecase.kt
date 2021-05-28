package com.elrond.erdkotlin.domain.sc

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.isDigitsOnly
import com.elrond.erdkotlin.utils.toHexString
import java.math.BigInteger
import java.util.*

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
        value: BigInteger = 0.toBigInteger(),
    ): Transaction {
        val transaction = Transaction(
            nonce = account.nonce,
            receiver = contractAddress,
            sender = account.address,
            chainID = networkConfig.chainID,
            gasPrice = gasPrice,
            gasLimit = gasLimit,
            value = value,
            data = args.fold(funcName) { it1, it2 -> it1 + "@${prepareArgument(it2)}" }
        )
        return sendTransactionUsecase.execute(transaction, wallet)
    }

    // source:
    // https://github.com/ElrondNetwork/elrond-sdk/blob/576fdc4bc0fa713738d8556600f04e6377c7623f/erdpy/contracts.py#L156
    private fun prepareArgument(arg: String): String {
        val hexPrefix = "0X"
        val argUpCase = arg.toUpperCase(Locale.ROOT)

        if (argUpCase.startsWith(hexPrefix)){
            return argUpCase.substring(startIndex = hexPrefix.length)
        }

        if (!argUpCase.isDigitsOnly()){
            throw IllegalArgumentException("unknown format for $arg")
        }

        val asNumber = argUpCase.toInt()
        val asHexstring = asNumber.toHexString()
        if (asHexstring.length % 2 == 1){
            return "0$asHexstring"
        }
        return asHexstring

    }

}
