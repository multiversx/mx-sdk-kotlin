package com.elrond.erdkotlin

import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.data.vm.responses.QueryContractResponse
import org.bouncycastle.util.encoders.Base64
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any

@RunWith(MockitoJUnitRunner::class)
class DataMapperTest {

    @Test
    fun `query return data must be well decoded and formatted`() {
        val oneHundredBase64 = String(Base64.encode(arrayOf(100.toByte()).toByteArray()))

        val queryResponseData = QueryContractResponse.Data(
            returnData = listOf(oneHundredBase64),
            returnCode = "",
            returnMessage = null,
            gasRemaining = 0.toBigInteger(),
            gasRefund = 0.toBigInteger(),
            outputAccounts = null,
            deletedAccounts = null,
            touchedAccounts = null,
            logs = null
        )

        val returnedData = queryResponseData.toDomain().returnData?.first()!!

        assertEquals(
            "ZA==",
            returnedData.asBase64
        )
        assertEquals(
            "64",
            returnedData.asHex
        )
        assertEquals(
            "d",
            returnedData.asString,
        )
        assertEquals(
            100.toBigInteger(),
            returnedData.asBigInt
        )
    }
}
