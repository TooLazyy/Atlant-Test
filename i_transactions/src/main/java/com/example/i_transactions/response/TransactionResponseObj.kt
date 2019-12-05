package com.example.i_transactions.response

import com.example.base.network.Transformable
import com.example.domain.TransactionBrief
import com.example.i_web_socket.BaseSocketResponse
import com.google.gson.annotations.SerializedName

data class TransactionResponseObj(
    @SerializedName("x") val x: XObj?
) : BaseSocketResponse(),
    Transformable<TransactionBrief> {

    override fun transform(): TransactionBrief =
        TransactionBrief(
            (x?.timestamp ?: 0L) * 1000,
            x?.hash ?: ""
        )
}

data class XObj(
    @SerializedName("time") val timestamp: Long?,
    @SerializedName("hash") val hash: String?
)