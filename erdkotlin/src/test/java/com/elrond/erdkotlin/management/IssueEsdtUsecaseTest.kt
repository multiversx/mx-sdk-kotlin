package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.IssueEsdtUsecase
import com.elrond.erdkotlin.domain.esdt.models.ManagementProperty
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IssueEsdtUsecaseTest {

    private val issueEsdtUsecase = IssueEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `tokenName name length should not be lower than 3`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "a",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenName name length should not be bigger than 20`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcefghijklmnopqrstuv",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenName name should be alphanumeric`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abc-efg",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenTicker name length should not be lower than 3`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcde",
                tokenTicker = "E",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenTicker name length should not be bigger than 10`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abc",
                tokenTicker = "ABCDEFGHIJK",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenTicker name should be uppercase`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcefg",
                tokenTicker = "ekt",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `tokenTicker name should be alphanumeric`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcefg",
                tokenTicker = "EK-T",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 3
            )
        }
    }

    @Test
    fun `numberOfDecimal should be positive`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcefg",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = -1
            )
        }
    }

    @Test
    fun `numberOfDecimal should not be bigger than 18`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenName = "abcefg",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 19
            )
        }
    }

    @Test
    fun `data should be well encoded`() {
        val transaction = issueEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenName = "ErdKotlin",
            tokenTicker = "EKT",
            initialSupply = 100000.toBigInteger(),
            numberOfDecimal = 3,
            managementProperties = mapOf(
                ManagementProperty.CanFreeze to true,
                ManagementProperty.CanWipe to false,
                ManagementProperty.CanPause to true,
                ManagementProperty.CanMint to true,
                ManagementProperty.CanBurn to true,
                ManagementProperty.CanChangeOwner to true,
                ManagementProperty.CanUpgrade to true,
                ManagementProperty.CanAddSpecialRoles to true
            )
        )

        assertEquals(
            "issue@4572644b6f746c696e@454b54@0186a0@03@63616e467265657a65@74727565@63616e57697065@66616c7365@63616e5061757365@74727565@63616e4d696e74@74727565@63616e4275726e@74727565@63616e4368616e67654f776e6572@74727565@63616e55706772616465@74727565@63616e4164645370656369616c526f6c6573@74727565",
            transaction.data
        )

    }

}
