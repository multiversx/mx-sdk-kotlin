package com.elrond.erdkotlin

import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import org.junit.Assert
import org.junit.Test

class IssueEsdtUsecaseTest {

    private val issueEsdtUsecase = ErdSdk.getIssueEsdtUsecase()

    @Test
    fun `tokenName name length should not be lower than 3`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            issueEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
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
                tokenName = "abcefg",
                tokenTicker = "EKT",
                initialSupply = 100000.toBigInteger(),
                numberOfDecimal = 19
            )
        }
    }

}