package com.elrond.erdkotlin.helper

import com.elrond.erdkotlin.domain.transaction.SendTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.SignTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.TransactionRepository
import com.elrond.erdkotlin.domain.transaction.models.TransactionHash
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

object TestUsecaseProvider {

    internal val transactionRepository = mock<TransactionRepository>().apply {
        whenever(sendTransaction(any())).thenReturn(
            TransactionHash("")
        )
    }

    internal val sendTransactionUsecase = SendTransactionUsecase(
        SignTransactionUsecase(),
        transactionRepository
    )
}
