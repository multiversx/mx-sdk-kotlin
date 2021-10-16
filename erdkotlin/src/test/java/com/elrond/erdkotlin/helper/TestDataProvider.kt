package com.elrond.erdkotlin.helper

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Wallet

object TestDataProvider {
    const val alicePrivateKey = "1a927e2af5306a9bb2ea777f73e06ecc0ac9aaa72fb4ea3fecf659451394cccf"

    val wallet = Wallet.createFromPrivateKey(alicePrivateKey)

    val account = Account(
        address = Address.fromBech32("erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz")
    )

    val networkConfig = NetworkConfig(
         chainID = "D",
         erdDenomination = 18,
         gasPerDataByte = 1500,
         erdGasPriceModifier = 0.01,
         erdLatestTagSoftwareVersion = "D1.1.51.0",
         erdMetaConsensusGroupSize = 58L,
         minGasLimit = 50000L,
         minGasPrice = 1000000000L,
         minTransactionVersion = 1,
         erdNumMetachainNodes = 58,
         erdNumNodesInShard = 58,
         erdNumShardsWithoutMeta = 3,
         erdRewardsTopUpGradientPoint = "3000000000000000000000000",
         erdRoundDuration = 6000,
         erdRoundsPerEpoch = 1200,
         erdShardConsensusGroupSize = 21,
         erdStartTime = 1616338800,
         erdTopUpFactor = "0.25"
    )

    private val transactionWithData = Transaction(
        nonce = 7,
        value = "10000000000000000000".toBigInteger(),
        sender = Address.fromBech32("erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz"),
        receiver = Address.fromBech32("erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r"),
        gasPrice = 1000000000,
        gasLimit = 70000,
        data = "for the book with stake",
        chainID = "1"
    )

    private val transactionWithoutData = Transaction(
        nonce = 8,
        value = "10000000000000000000".toBigInteger(),
        sender = Address.fromBech32("erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz"),
        receiver = Address.fromBech32("erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r"),
        gasPrice = 1000000000,
        gasLimit = 50000,
        chainID = "1"
    )

    fun transactionWithData() = transactionWithData.copy()
    fun transactionWithoutData() = transactionWithoutData.copy()
}
