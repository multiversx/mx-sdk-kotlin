package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.PauseAccountEsdtUsecase
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PauseAccountEsdtUsecaseTest {

    private val freezeEsdtUsecase = PauseAccountEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded freeze`() {
        val transaction = freezeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            action = PauseAccountEsdtUsecase.Action.Pause
        )

        assertEquals(
            "pause@4552444b54363937322d623665643261",
            transaction.data,
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
            action = PauseAccountEsdtUsecase.Action.UnPause
        )

        assertEquals(
            "unPause@4552444b54363937322d623665643261",
            transaction.data,
        )

    }

}
