package com.example.i_transactions

import com.example.domain.TransactionBrief
import com.example.i_transactions.repository.TransactionsRepository
import kotlinx.coroutines.flow.Flow

class TransactionsInteractor(
    private val repository: TransactionsRepository
) {

    suspend fun getTransactions(): Flow<TransactionBrief> =
        repository.getTransactions()

    fun unsubscribeTransaction() = repository.unsubscribeTransactions()
}