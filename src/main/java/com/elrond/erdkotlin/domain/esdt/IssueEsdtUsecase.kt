package com.elrond.erdkotlin.domain.esdt

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.esdt.EsdtConstants.ESDT_SYSTEM_SC
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.sc.ScUtils
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.toHex
import java.math.BigInteger

class IssueEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase
) {

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        tokenName: String,
        tokenTicker: String,
        initialSupply: BigInteger,
        numberOfDecimal: Int,
        sender: Address = account.address,
        canFreeze: Boolean? = null,
        canWipe: Boolean? = null,
        canPause: Boolean? = null,
        canMint: Boolean? = null,
        canBurn: Boolean? = null,
        canChangeOwner: Boolean? = null,
        canUpgrade: Boolean? = null,
        canAddSpecialRoles: Boolean? = null
    ) {
        if (!tokenName.matches("^[A-Za-z0-9]{3,20}$".toRegex())) {
            throw IllegalArgumentException("tokenName length should be between 3 and 20 characters " +
                        "and alphanumeric only")
        }
        if (!tokenTicker.matches("^[A-Z0-9]{3,10}$".toRegex())) {
            throw IllegalArgumentException("tokenTicker length should be between 3 and 10 characters " +
                        "and alphanumeric uppercase only")
        }
        if (numberOfDecimal < 0 || numberOfDecimal > 18) {
            throw IllegalArgumentException("numberOfDecimal should be between 0 and 18")
        }
        val list = mutableListOf(
            tokenName.toHex(),
            tokenTicker.toHex(),
            initialSupply.toHex(),
            ScUtils.prepareArgument(numberOfDecimal.toString())
        ).apply {
            fun prepareBoolean(key: String, value: Boolean) =
                "${key.toHex()}@${value.toString().toHex()}"

            if (canFreeze != null) {
                add(prepareBoolean("canFreeze", canFreeze))
            }
            if (canWipe != null) {
                add(prepareBoolean("canWipe", canWipe))
            }
            if (canPause != null) {
                add(prepareBoolean("canPause", canPause))
            }
            if (canMint != null) {
                add(prepareBoolean("canMint", canMint))
            }
            if (canBurn != null) {
                add(prepareBoolean("canBurn", canBurn))
            }
            if (canChangeOwner != null) {
                add(prepareBoolean("canChangeOwner", canChangeOwner))
            }
            if (canUpgrade != null) {
                add(prepareBoolean("canUpgrade", canUpgrade))
            }
            if (canAddSpecialRoles != null) {
                add(prepareBoolean("canAddSpecialRoles", canAddSpecialRoles))
            }
        }
        sendTransactionUsecase.execute(
            Transaction(
                sender = sender,
                receiver = ESDT_SYSTEM_SC,
                value = issueValue,
                gasLimit = 60000000,
                data = list.fold("issue") { it1, it2 -> "$it1@$it2" },
                chainID = networkConfig.chainID,
                nonce = account.nonce,
            ),
            wallet
        )

    }

    companion object {
        private val issueValue = 50000000000000000.toBigInteger() // 0.05 EGLD
    }

}