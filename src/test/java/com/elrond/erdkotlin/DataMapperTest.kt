package com.elrond.erdkotlin

import com.elrond.erdkotlin.data.toDomain
import com.elrond.erdkotlin.data.toEsdtProperties
import com.elrond.erdkotlin.data.toSpecialRoles
import com.elrond.erdkotlin.data.vm.responses.QueryContractResponse
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRole
import com.elrond.erdkotlin.domain.wallet.models.Address
import org.bouncycastle.util.encoders.Base64
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigInteger

@RunWith(MockitoJUnitRunner::class)
class DataMapperTest {

    @Test
    fun `query return data must be well decoded and formatted`() {
        val oneHundredBase64 = String(Base64.encode(arrayOf(100.toByte()).toByteArray()))

        val queryResponseData = QueryContractResponse.Data(
            returnData = listOf(oneHundredBase64),
            returnCode = "",
            returnMessage = null,
            gasRemaining = BigInteger.ZERO,
            gasRefund = BigInteger.ZERO,
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

    @Test
    fun `esdt properties should be well formatted`() {
        val response = QueryContractResponse.Data(
            returnData = listOf("QWxpY2VUb2tlbnM=",
                "RnVuZ2libGVFU0RU",
                "2DSJxJNAmou8TU9f4WQo7rpyJ822eZVUQYwnabJM5hk=",
                "MTAwMDAwMDAwMDA=",
                "MA==",
                "TnVtRGVjaW1hbHMtNg==",
                "SXNQYXVzZWQtZmFsc2U=",
                "Q2FuVXBncmFkZS10cnVl",
                "Q2FuTWludC10cnVl",
                "Q2FuQnVybi10cnVl",
                "Q2FuQ2hhbmdlT3duZXItZmFsc2U=",
                "Q2FuUGF1c2UtdHJ1ZQ==",
                "Q2FuRnJlZXplLXRydWU=",
                "Q2FuV2lwZS10cnVl",
                "Q2FuQWRkU3BlY2lhbFJvbGVzLXRydWU=",
                "Q2FuVHJhbnNmZXJORlRDcmVhdGVSb2xlLWZhbHNl",
                "TkZUQ3JlYXRlU3RvcHBlZC1mYWxzZQ==",
                "TnVtV2lwZWQtMA=="
            ),
            returnCode = "",
            returnMessage = null,
            gasRemaining = BigInteger.ZERO,
            gasRefund = BigInteger.ZERO,
            outputAccounts = null,
            deletedAccounts = null,
            touchedAccounts = null,
            logs = null
        ).toDomain()

        val esdtProperties = response.toEsdtProperties()
        assertEquals(esdtProperties.tokenName, "AliceTokens")
        assertEquals(esdtProperties.tokenType, "FungibleESDT")
        assertEquals(esdtProperties.address.bech32, "erd1mq6gn3yngzdgh0zdfa07zepga6a8yf7dkeue24zp3snknvjvucvs37hmrq")
        assertEquals(esdtProperties.tokenName, "AliceTokens")
        assertEquals(esdtProperties.totalSupply, 10000000000.toBigInteger())
        assertEquals(esdtProperties.burntValue, BigInteger.ZERO)
        assertEquals(esdtProperties.numberOfDecimals, 6L)
        assertEquals(esdtProperties.isPaused, false)
        assertEquals(esdtProperties.canUpgrade, true)
        assertEquals(esdtProperties.canMint, true)
        assertEquals(esdtProperties.canBurn, true)
        assertEquals(esdtProperties.canChangeOwner, false)
        assertEquals(esdtProperties.canPause, true)
        assertEquals(esdtProperties.canFreeze, true)
        assertEquals(esdtProperties.canWipe, true)
        assertEquals(esdtProperties.canAddSpecialRoles, true)
        assertEquals(esdtProperties.canTransferNftCreateRole, false)
        assertEquals(esdtProperties.nftCreateStopped, false)
        assertEquals(esdtProperties.numWiped, BigInteger.ZERO)

    }

    @Test
    fun `esdt specialRoles should be well formatted`() {
        val response = QueryContractResponse.Data(
            returnData = listOf("ZXJkMTM2cmw4NzhqMDltZXYyNGd6cHk3MGsyd2ZtM3htdmo1dWN3eGZmczl2NXQ1c2sza3NodHN6ejI1ejk6RVNEVFJvbGVMb2NhbEJ1cm4=", "ZXJkMWt6enYydXc5N3E1azltdDQ1OHFrM3E5dTNjd2h3cXlrdnlrNTk4cTJmNnd3eDdndnJkOXM4a3N6eGs6RVNEVFJvbGVORlRBZGRRdWFudGl0eSxFU0RUUm9sZU5GVEJ1cm4="),
            returnCode = "",
            returnMessage = null,
            gasRemaining = BigInteger.ZERO,
            gasRefund = BigInteger.ZERO,
            outputAccounts = null,
            deletedAccounts = null,
            touchedAccounts = null,
            logs = null
        ).toDomain()

        val specialRoles = response.toSpecialRoles()
        assertNotNull(specialRoles)
        val addresses = specialRoles!!.addresses


        val addr0 = Address.fromBech32("erd136rl878j09mev24gzpy70k2wfm3xmvj5ucwxffs9v5t5sk3kshtszz25z9")
        val specialRoles0 = addresses.getValue(addr0)
        assert(specialRoles0.size == 1)
        assertEquals(specialRoles0, listOf(EsdtSpecialRole.ESDTRoleLocalBurn))

        val addr1 = Address.fromBech32("erd1kzzv2uw97q5k9mt458qk3q9u3cwhwqykvyk598q2f6wwx7gvrd9s8kszxk")
        val specialRoles1 = addresses.getValue(addr1)
        assert(specialRoles1.size == 2)

        assertEquals(specialRoles1, listOf(EsdtSpecialRole.ESDTRoleNFTAddQuantity, EsdtSpecialRole.ESDTRoleNFTBurn))

    }
}
