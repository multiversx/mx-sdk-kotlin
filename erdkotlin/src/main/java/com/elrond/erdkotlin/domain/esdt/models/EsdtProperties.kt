package com.elrond.erdkotlin.domain.esdt.models

import com.elrond.erdkotlin.domain.wallet.models.Address
import java.math.BigInteger

data class EsdtProperties(
    val tokenName: String,
    val tokenType: String,
    val address: Address,
    val totalSupply: BigInteger,
    val burntValue: BigInteger,
    val numberOfDecimals: Long,
    val isPaused: Boolean,
    val canUpgrade: Boolean,
    val canMint: Boolean,
    val canBurn: Boolean,
    val canChangeOwner: Boolean,
    val canPause: Boolean,
    val canFreeze: Boolean,
    val canWipe: Boolean,
    val canAddSpecialRoles: Boolean,
    val canTransferNftCreateRole: Boolean,
    val nftCreateStopped: Boolean,
    val numWiped: BigInteger,
)
