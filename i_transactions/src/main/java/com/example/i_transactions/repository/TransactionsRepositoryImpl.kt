package com.example.i_transactions.repository

import com.example.domain.TransactionBrief
import com.example.i_transactions.request.UNCONFIRMED_FILTER
import com.example.i_transactions.request.UnconfirmedTransactionsRequest
import com.example.i_transactions.response.TransactionResponseObj
import com.example.i_web_socket.WebSocketSource
import com.example.i_web_socket.WebSocketSubscribeRequest
import com.example.i_web_socket.WebSocketUnsubscribeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionsRepositoryImpl(
    private val socketSource: WebSocketSource
) : TransactionsRepository {

    override suspend fun getTransactions(): Flow<TransactionBrief> =
        socketSource
            .subscribe(
                WebSocketSubscribeRequest(
                    UNCONFIRMED_FILTER,
                    UnconfirmedTransactionsRequest.SubscribeToUnconfirmed,
                    TransactionResponseObj::class.java
                )
            )
            .map { it.transform() }

    override fun unsubscribeTransactions() =
        socketSource.unsubscribe(
            WebSocketUnsubscribeRequest(
                UnconfirmedTransactionsRequest.UnsubscribeToUnconfirmed,
                UnconfirmedTransactionsRequest.SubscribeToUnconfirmed
            )
        )
}