package com.elrond.erdkotlin.data.transaction.responses

import java.math.BigInteger

internal class GetTransactionInfoResponse(
    val transaction: TransactionInfoData
) {
    data class TransactionInfoData(
        val type: String,
        val nonce: Long,
        val round: Long,
        val epoch: Long,
        val value: BigInteger,
        val sender: String,
        val receiver: String,
        val senderUsername: String?,
        val receiverUsername: String?,
        val gasPrice: Long,
        val gasLimit: Long,
        val data: String?,
        val signature: String,
        val sourceShard: Long,
        val destinationShard: Long,
        val blockNonce: Long,
        val timestamp: Long,
        val miniBlockHash: String?,
        val blockHash: String?,
        val status: String,
        val hyperblockNonce: Long?,
        val smartContractResults: List<ScResult>?
    )

    data class ScResult(
        val hash: String?,
        val nonce: Long,
        val value: BigInteger,
        val receiver: String,
        val sender: String,
        val data: String?,
        val prevTxHash: String,
        val originalTxHash: String,
        val gasLimit: Long,
        val gasPrice: Long,
        val callType: String
    )

}
