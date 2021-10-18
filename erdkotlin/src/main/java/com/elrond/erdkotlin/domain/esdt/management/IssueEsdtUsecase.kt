package com.elrond.erdkotlin.domain.esdt.management

import com.elrond.erdkotlin.domain.account.models.Account
import com.elrond.erdkotlin.domain.esdt.EsdtConstants
import com.elrond.erdkotlin.domain.esdt.models.ManagementProperty
import com.elrond.erdkotlin.domain.networkconfig.models.NetworkConfig
import com.elrond.erdkotlin.domain.sc.ScUtils
import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.domain.wallet.models.Wallet
import com.elrond.erdkotlin.utils.toHex
import com.elrond.erdkotlin.utils.toHexString
import java.math.BigInteger

class IssueEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase
) {

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        tokenName: String,
        tokenTicker: String,
        initialSupply: BigInteger,
        numberOfDecimal: Int,
        canFreeze: Boolean? = null,
        canWipe: Boolean? = null,
        canPause: Boolean? = null,
        canMint: Boolean? = null,
        canBurn: Boolean? = null,
        canChangeOwner: Boolean? = null,
        canUpgrade: Boolean? = null,
        canAddSpecialRoles: Boolean? = null
    ) {
        execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = gasPrice,
            tokenName = tokenName,
            tokenTicker = tokenTicker,
            initialSupply = initialSupply,
            numberOfDecimal = numberOfDecimal,
            managementProperties = mutableMapOf<ManagementProperty, Boolean>().apply {
                if (canFreeze != null) {
                    put(ManagementProperty.CanFreeze, canFreeze)
                }
                if (canWipe != null) {
                    put(ManagementProperty.CanWipe, canWipe)
                }
                if (canPause != null) {
                    put(ManagementProperty.CanPause, canPause)
                }
                if (canMint != null) {
                    put(ManagementProperty.CanMint, canMint)
                }
                if (canBurn != null) {
                    put(ManagementProperty.CanBurn, canBurn)
                }
                if (canChangeOwner != null) {
                    put(ManagementProperty.CanChangeOwner, canChangeOwner)
                }
                if (canUpgrade != null) {
                    put(ManagementProperty.CanUpgrade, canUpgrade)
                }
                if (canAddSpecialRoles != null) {
                    put(ManagementProperty.CanAddSpecialRoles, canAddSpecialRoles)
                }
            }
        )
    }

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        tokenName: String,
        tokenTicker: String,
        initialSupply: BigInteger,
        numberOfDecimal: Int,
        managementProperties: Map<ManagementProperty, Boolean> = emptyMap()
    ): Transaction {
        if (!tokenName.matches("^[A-Za-z0-9]{3,20}$".toRegex())) {
            throw IllegalArgumentException(
                "tokenName length should be between 3 and 20 characters " +
                        "and alphanumeric only"
            )
        }
        if (!tokenTicker.matches("^[A-Z0-9]{3,10}$".toRegex())) {
            throw IllegalArgumentException(
                "tokenTicker length should be between 3 and 10 characters " +
                        "and alphanumeric uppercase only"
            )
        }
        if (numberOfDecimal < 0 || numberOfDecimal > 18) {
            throw IllegalArgumentException("numberOfDecimal should be between 0 and 18")
        }
        val args = mutableListOf(
            tokenName.toHex(),
            tokenTicker.toHex(),
            initialSupply.toHex(),
            numberOfDecimal.toHexString()
        ).apply {
            if (managementProperties.isNotEmpty()) {
                addAll(managementProperties.map { (key, value) ->
                    ScUtils.prepareBooleanArgument(key.serializedName, value)
                })
            }
        }

        return sendTransactionUsecase.execute(
            Transaction(
                sender = account.address,
                receiver = EsdtConstants.ESDT_SC_ADDR,
                value = issuingCost,
                gasLimit = 60000000,
                gasPrice = gasPrice,
                data = args.fold("issue") { it1, it2 -> "$it1@$it2" },
                chainID = networkConfig.chainID,
                nonce = account.nonce,
            ),
            wallet
        )

    }

    companion object {
        private val issuingCost = "50000000000000000".toBigInteger() // 0.05 EGLD
    }

}
