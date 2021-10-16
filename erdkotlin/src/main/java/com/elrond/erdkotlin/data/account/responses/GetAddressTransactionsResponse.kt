package com.elrond.erdkotlin.data.account.responses

import java.math.BigInteger

internal data class GetAddressTransactionsResponse(
    val transactions: List<TransactionOnNetworkData>
) {
    data class TransactionOnNetworkData(
        val sender: String,
        val receiver: String,
        val senderUsername: String?,
        val receiverUsername: String?,
        val nonce: Long,
        val value: BigInteger,
        val gasPrice: Long,
        val gasLimit: Long,
        val signature: String,
        val hash: String,
        val data: String?,
        val status: String,
        val timestamp: Long,
        val gasUsed: Long,
        val receiverShard: Long,
        val senderShard: Long,
        val miniBlockHash: String,
        val round: Long,
        val searchOrder: Long,
        val fee: String,
        val scResults: List<ScResult>?,
        val hyperblockNonce: Long?
    ) {
        // source : https://github.com/ElrondNetwork/elrond-go/blob/2be09d2377993cda87cef7b4167c915d8ea5f163/data/transaction/apiTransactionResult.go#L57
        data class ScResult(
            val hash: String?,
            val nonce: Long,
            val gasLimit: Long,
            val gasPrice: Long,
            val value: BigInteger,
            val sender: String,
            val receiver: String,
            val relayedValue: String?,
            val data: String?,
            val prevTxHash: String,
            val originalTxHash: String,
            val callType: String,
            val relayerAddress: String?,
            val code: String?,
            val codeMetadata: String?,
            val returnMessage: String?,
            val originalSender: String?,
        )
    }
}
