package com.elrond.erdkotlin.data

import com.elrond.erdkotlin.data.account.responses.GetAccountResponse
import com.elrond.erdkotlin.data.account.responses.GetAddressTransactionsResponse
import com.elrond.erdkotlin.data.networkconfig.GetNetworkConfigResponse
import com.elrond.erdkotlin.data.transaction.responses.GetTransactionInfoResponse
import com.elrond.erdkotlin.data.vm.responses.QueryContractDigitResponse
import com.elrond.erdkotlin.data.vm.responses.QueryContractResponse
import com.elrond.erdkotlin.data.vm.responses.QueryContractStringResponse
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.esdt.models.EsdtProperties
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRole
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRoles
import com.elrond.erdkotlin.domain.transaction.models.TransactionInfo
import com.elrond.erdkotlin.domain.transaction.models.TransactionOnNetwork
import com.elrond.erdkotlin.domain.vm.query.integer.QueryContractDigitOutput
import com.elrond.erdkotlin.domain.vm.query.QueryContractOutput
import com.elrond.erdkotlin.domain.vm.query.string.QueryContractStringOutput
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.utils.toHexString
import org.bouncycastle.util.encoders.Base64
import java.math.BigInteger

internal fun GetAccountResponse.AccountData.toDomain(address: Address) = Account(
    address = address,
    nonce = nonce,
    balance = balance,
    code = code,
    username = username
)

internal fun GetAddressTransactionsResponse.TransactionOnNetworkData.toDomain() =
    TransactionOnNetwork(
        sender = Address.fromBech32(sender),
        receiver = Address.fromBech32(receiver),
        senderUsername = senderUsername,
        receiverUsername = receiverUsername,
        nonce = nonce,
        value = value,
        gasPrice = gasPrice,
        gasLimit = gasLimit,
        signature = signature,
        hash = hash,
        data = data?.let { String(Base64.decode(data)) },
        status = status,
        timestamp = timestamp,
        gasUsed = gasUsed,
        receiverShard = receiverShard,
        senderShard = senderShard,
        miniBlockHash = miniBlockHash,
        round = round,
        searchOrder = searchOrder,
        fee = fee,
        scResults = scResults?.map { scResult -> scResult.toDomain() },
        hyperblockNonce = hyperblockNonce
    )

internal fun GetAddressTransactionsResponse.TransactionOnNetworkData.ScResult.toDomain() =
    TransactionOnNetwork.ScResult(
        hash = hash,
        nonce = nonce,
        gasLimit = gasLimit,
        gasPrice = gasPrice,
        value = value,
        sender = sender,
        receiver = receiver,
        relayedValue = relayedValue,
        data = data,
        prevTxHash = prevTxHash,
        originalTxHash = originalTxHash,
        callType = callType,
        relayerAddress = relayerAddress,
        code = code,
        codeMetadata = codeMetadata,
        returnMessage = returnMessage,
        originalSender = originalSender,
    )

internal fun GetTransactionInfoResponse.TransactionInfoData.toDomain() = TransactionInfo(
    type = type,
    nonce = nonce,
    round = round,
    epoch = epoch,
    value = value,
    sender = Address.fromBech32(sender),
    receiver = Address.fromBech32(receiver),
    senderUsername = senderUsername,
    receiverUsername = receiverUsername,
    gasPrice = gasPrice,
    gasLimit = gasLimit,
    data = data?.let { String(Base64.decode(data)) },
    signature = signature,
    sourceShard = sourceShard,
    destinationShard = destinationShard,
    blockNonce = blockNonce,
    miniBlockHash = miniBlockHash,
    blockHash = blockHash,
    status = status,
    hyperblockNonce = hyperblockNonce
)

internal fun QueryContractResponse.Data.toDomain() = QueryContractOutput(
    returnData = returnData?.map { base64 ->
        val bytes = Base64.decode(base64)
        val asHex = bytes.toHexString()
        QueryContractOutput.ReturnData(
            asBase64 = base64,
            asString = String(bytes),
            asHex = asHex,
            asBigInt = BigInteger(asHex.ifEmpty { "0" }, 16)
        )
    },
    returnCode = returnCode,
    returnMessage = returnMessage,
    gasRemaining = gasRemaining,
    gasRefund = gasRefund,
    outputAccounts = outputAccounts,
)

internal fun QueryContractStringResponse.toDomain() = QueryContractStringOutput(
    data = data
)

internal fun QueryContractDigitResponse.toDomain() = QueryContractDigitOutput(
    data = data
)

internal fun GetNetworkConfigResponse.NetworkConfigData.toDomain() = NetworkConfig(
    chainID = chainID,
    erdDenomination = erdDenomination,
    gasPerDataByte = gasPerDataByte,
    erdGasPriceModifier = erdGasPriceModifier,
    erdLatestTagSoftwareVersion = erdLatestTagSoftwareVersion,
    erdMetaConsensusGroupSize = erdMetaConsensusGroupSize,
    minGasLimit = minGasLimit,
    minGasPrice = minGasPrice,
    minTransactionVersion = minTransactionVersion,
    erdNumMetachainNodes = erdNumMetachainNodes,
    erdNumNodesInShard = erdNumNodesInShard,
    erdNumShardsWithoutMeta = erdNumShardsWithoutMeta,
    erdRewardsTopUpGradientPoint = erdRewardsTopUpGradientPoint,
    erdRoundDuration = erdRoundDuration,
    erdRoundsPerEpoch = erdRoundsPerEpoch,
    erdShardConsensusGroupSize = erdShardConsensusGroupSize,
    erdStartTime = erdStartTime,
    erdTopUpFactor = erdTopUpFactor
)

internal fun QueryContractOutput.toEsdtProperties() = requireNotNull(returnData).let { returnDatas ->

    // format is `key-value`
    fun <T> QueryContractOutput.ReturnData.extractValue(key: String, convertor: (String) -> T): T =
        asString.split('-').let { keyValue ->
            if (keyValue.size != 2 || keyValue.first() != key) {
                throw IllegalArgumentException("cannot extract value for key `$key` in `$asString`." +
                        " Expected format is `key-value`")
            }
            return convertor.invoke(keyValue.last())
        }

    fun QueryContractOutput.ReturnData.extractBoolean(key: String): Boolean =
        extractValue(key) { value -> value.toBoolean() }

    fun QueryContractOutput.ReturnData.extractLong(key: String): Long =
        extractValue(key) { value -> value.toLong() }

    fun QueryContractOutput.ReturnData.extractBigInteger(key: String): BigInteger =
        extractValue(key) { value -> value.toBigInteger() }

    EsdtProperties(
        tokenName = returnDatas[0].asString,
        tokenType = returnDatas[1].asString,
        address = Address.fromHex(returnDatas[2].asHex),
        totalSupply = returnDatas[3].asString.toBigInteger(), // cannot use asBigInteger
        burntValue = returnDatas[4].asString.toBigInteger(), // cannot use asBigInteger
        numberOfDecimals = returnDatas[5].extractLong("NumDecimals"),
        isPaused = returnDatas[6].extractBoolean("IsPaused"),
        canUpgrade = returnDatas[7].extractBoolean("CanUpgrade"),
        canMint = returnDatas[8].extractBoolean("CanMint"),
        canBurn = returnDatas[9].extractBoolean("CanBurn"),
        canChangeOwner = returnDatas[10].extractBoolean("CanChangeOwner"),
        canPause = returnDatas[11].extractBoolean("CanPause"),
        canFreeze = returnDatas[12].extractBoolean("CanFreeze"),
        canWipe = returnDatas[13].extractBoolean("CanWipe"),
        canAddSpecialRoles = returnDatas[14].extractBoolean("CanAddSpecialRoles"),
        canTransferNftCreateRole = returnDatas[15].extractBoolean("CanTransferNFTCreateRole"),
        nftCreateStopped = returnDatas[16].extractBoolean("NFTCreateStopped"),
        numWiped = returnDatas[17].extractBigInteger("NumWiped")
    )
}

// format is : `address:specialRole,specialRole,..`
internal fun QueryContractOutput.toSpecialRoles(): EsdtSpecialRoles? {
    val formattedData = returnData?.map { returnData ->
        val keyValue = returnData.asString.split(':')
        if (keyValue.size != 2) {
            throw IllegalArgumentException(
                "cannot extract key/value in `${returnData.asString}`. Expected format is `key:value`"
            )
        }
        val key = Address.fromBech32(keyValue.first())
        val value = keyValue.last().split(',').map { specialRole ->
            EsdtSpecialRole.valueOf(specialRole)
        }
        Pair(key, value)
    }
    return formattedData?.toMap()?.let { EsdtSpecialRoles(it) }

}
