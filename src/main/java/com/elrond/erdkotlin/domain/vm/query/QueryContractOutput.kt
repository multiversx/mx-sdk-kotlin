package com.elrond.erdkotlin.domain.vm.query

import com.google.gson.internal.LinkedTreeMap
import java.math.BigInteger

data class QueryContractOutput(
    val returnData: List<ReturnData>?,
    val returnCode: String,
    val returnMessage: String?,
    val gasRemaining: BigInteger,
    val gasRefund: BigInteger,
    val outputAccounts: LinkedTreeMap<String, OutputAccount>?
) {
    data class ReturnData(
        val asBase64: String,
        val asString: String,
        val asHex: String,
        val asBigInt: BigInteger
    )

    data class OutputAccount(
        val address: String,
        val nonce: Long,
        val balance: BigInteger?,
        val balanceDelta: BigInteger,
        val storageUpdates: LinkedTreeMap<String, StorageUpdate>?,
        val callType: Long,

        // Keeping those as placeholders for future development
        // https://github.com/ElrondNetwork/elrond-go/blob/master/core/vmcommon/output.go
        val code: Any?,
        val codeMetadata: Any?,
        val data: Any?,
        val gasLimit: Any?,
    ){
        data class StorageUpdate(
            val offset: String
        )
    }
}
