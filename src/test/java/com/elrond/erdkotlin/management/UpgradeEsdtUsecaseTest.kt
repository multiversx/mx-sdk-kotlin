package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.UpgradeEsdtUsecase
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
class UpgradeEsdtUsecaseTest {

    private val upgradeEsdtUsecase = UpgradeEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded`() {
        val transaction = upgradeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            managementProperties = mapOf(
                ManagementProperty.CanFreeze to true,
                ManagementProperty.CanBurn to false,
                ManagementProperty.CanWipe to true
            )
        )

        assertEquals(
            "controlChanges@4552444b54363937322d623665643261@63616e467265657a65@74727565@63616e4275726e@66616c7365@63616e57697065@74727565",
            transaction.data,
        )

    }

    @Test
    fun `managementProperties should not be empty`() {
        Assert.assertThrows(IllegalArgumentException::class.java) {
            upgradeEsdtUsecase.execute(
                account = account,
                wallet = wallet,
                networkConfig = networkConfig,
                gasPrice = networkConfig.minGasPrice,
                tokenIdentifier = "ERDKT6972-b6ed2a",
                managementProperties = emptyMap()
            )
        }
    }

}
