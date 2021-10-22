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
        val hyperblockNonce: Long?
    )
}
