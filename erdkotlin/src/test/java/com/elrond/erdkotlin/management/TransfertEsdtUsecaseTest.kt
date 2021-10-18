package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.TransferEsdtUsecase
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
class TransfertEsdtUsecaseTest {

    private val transfertEsdtUsecase = TransferEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded`() {
        val transaction = transfertEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            receiver = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            tokenIdentifier = "ERDKT6972-b6ed2a",
            valueToTransfer = "10".toBigInteger()
        )

        assertEquals(
            "ESDTTransfer@4552444b54363937322d623665643261@0a",
            transaction.data,
        )

    }

    @Test
    fun `data should be well encoded for smartContract`() {
        val transaction = transfertEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            receiver = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            tokenIdentifier = "ERDKT6972-b6ed2a",
            valueToTransfer = "10".toBigInteger(),
            funcName = "func",
            funcArgs = listOf("255", "0x5745474c442d616263646566", "0xDEADBEEF")
        )

        assertEquals(
            "ESDTTransfer@4552444b54363937322d623665643261@0a@66756e63@FF@5745474C442D616263646566@DEADBEEF",
            transaction.data,
        )

    }

}
