package com.example.i_transactions.repository

import com.example.domain.TransactionBrief
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    suspend fun getTransactions(): Flow<TransactionBrief>

    fun unsubscribeTransactions()
}