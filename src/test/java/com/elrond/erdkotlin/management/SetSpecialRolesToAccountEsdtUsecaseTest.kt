package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.SetSpecialRolesEsdtUsecase
import com.elrond.erdkotlin.domain.esdt.models.EsdtSpecialRole
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
class SetSpecialRolesToAccountEsdtUsecaseTest {

    private val setSpecialRolesEsdtUsecase = SetSpecialRolesEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded freeze`() {
        val transaction = setSpecialRolesEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            addressToUpdate = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            specialRoles = listOf(EsdtSpecialRole.ESDTRoleLocalMint, EsdtSpecialRole.ESDTRoleLocalBurn),
            action = SetSpecialRolesEsdtUsecase.Action.Set
        )

        assertEquals(
            "setSpecialRole@4552444b54363937322d623665643261@f2f341214199970dcc3c086dc0e797de79f7accb91c94472ef49166cb5fbaac4@45534454526f6c654c6f63616c4d696e74@45534454526f6c654c6f63616c4275726e",
            transaction.data
        )

    }

    @Test
    fun `data should be well encoded unfreeze`() {
        val transaction = setSpecialRolesEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            addressToUpdate = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
            specialRoles = listOf(EsdtSpecialRole.ESDTRoleNFTAddQuantity, EsdtSpecialRole.ESDTRoleNFTBurn),
            action = SetSpecialRolesEsdtUsecase.Action.Unset
        )

        assertEquals(
            "unSetSpecialRole@4552444b54363937322d623665643261@f2f341214199970dcc3c086dc0e797de79f7accb91c94472ef49166cb5fbaac4@45534454526f6c654e46544164645175616e74697479@45534454526f6c654e46544275726e",
            transaction.data
        )

    }

}