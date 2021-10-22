package com.elrond.erdkotlin.domain.transaction.models

import com.elrond.erdkotlin.domain.wallet.models.Address
import java.math.BigInteger

data class TransactionInfo(
    val txHash: String,
    val type: String,
    val nonce: Long,
    val round: Long,
    val epoch: Long,
    val value: BigInteger,
    val sender: Address,
    val receiver: Address,
    val senderUsername: String?,
    val receiverUsername: String?,
    val gasPrice: Long,
    val gasLimit: Long,
    val data: String?, // base64 encoded
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
) {
    data class ScResult(
        val hash: String?,
        val nonce: Long,
        val value: BigInteger,
        val receiver: Address,
        val sender: Address,
        val data: String?, // not base64 encoded
        val prevTxHash: String,
        val originalTxHash: String,
        val gasLimit: Long,
        val gasPrice: Long,
        val callType: String
    )
}
