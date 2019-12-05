package com.example.i_transactions.request

import com.google.gson.annotations.SerializedName

const val UNCONFIRMED_FILTER = "utx"
private const val SUBSCRIBE_UNCONFIRMED = "unconfirmed_sub"
private const val UNSUBSCRIBE_UNCONFIRMED = "unconfirmed_unsub"

sealed class UnconfirmedTransactionsRequest(
    @SerializedName("op") val option: String
) {

    object SubscribeToUnconfirmed : UnconfirmedTransactionsRequest(SUBSCRIBE_UNCONFIRMED)
    object UnsubscribeToUnconfirmed : UnconfirmedTransactionsRequest(UNSUBSCRIBE_UNCONFIRMED)
}