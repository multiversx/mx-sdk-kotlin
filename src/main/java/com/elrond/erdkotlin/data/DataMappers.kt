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

internal fun GetAddressTransactionsResponse.TransactionOnNetworkData.toDomain() = TransactionOnNetwork(
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
    data = data,
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

internal fun GetAddressTransactionsResponse.TransactionOnNetworkData.ScResult.toDomain() = TransactionOnNetwork.ScResult(
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
    data = data,
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
