package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.FreezeAccountEsdtUsecase
import com.elrond.erdkotlin.domain.wallet.models.Address
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FreezeEsdtUsecaseTest {

    private val freezeEsdtUsecase = FreezeAccountEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded freeze`() {
        val transaction = freezeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            addressToUpdate = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            action = FreezeAccountEsdtUsecase.Action.Freeze
        )

        assertEquals(
            "freeze@4552444b54363937322d623665643261@f2f341214199970dcc3c086dc0e797de79f7accb91c94472ef49166cb5fbaac4",
            transaction.data
        )

    }

    @Test
    fun `data should be well encoded unfreeze`() {
        val transaction = freezeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            addressToUpdate = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            action = FreezeAccountEsdtUsecase.Action.UnFreeze
        )

        assertEquals(
            "unFreeze@4552444b54363937322d623665643261@f2f341214199970dcc3c086dc0e797de79f7accb91c94472ef49166cb5fbaac4",
            transaction.data
        )

    }

}
