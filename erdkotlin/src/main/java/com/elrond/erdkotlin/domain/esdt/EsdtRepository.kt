package com.elrond.erdkotlin.domain.esdt

import com.elrond.erdkotlin.domain.esdt.models.EsdtProperties
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRoles
import com.elrond.erdkotlin.domain.esdt.models.EsdtTokenBalance
import com.elrond.erdkotlin.domain.esdt.models.EsdtToken
import com.elrond.erdkotlin.domain.wallet.models.Address

internal interface EsdtRepository {

    // Get all ESDT tokens for an address
    fun getEsdtTokens(address: Address): Map<String, EsdtToken>

    // Get balance for an address and an ESDT token
    fun getEsdtBalance(address: Address, tokenIdentifier: String): EsdtTokenBalance

    // Get all issued ESDT tokens
    fun getAllEsdtIssued(): List<String>

    // Get ESDT token properties
    fun getEsdtProperties(tokenIdentifier: String): EsdtProperties

    // Get special roles for a token
    fun getEsdtSpecialRoles(tokenIdentifier: String): EsdtSpecialRoles?

}
