package com.example.atlanttest.presentation.features.transactions

import com.example.atlanttest.presentation.base.BaseViewState
import com.example.domain.TransactionBrief
import com.example.domain.UserProfile
import com.example.i_web_socket.SocketState

class TransactionsState : BaseViewState() {

    var userProfile: UserProfile? = null
    val hasProfileInfo: Boolean
        get() = userProfile != null
    val firstName: String
        get() = userProfile?.profiles?.firstOrNull()?.firstName ?: ""
    val lastName: String
        get() = userProfile?.profiles?.firstOrNull()?.lastName ?: ""
    val email: String
        get() = userProfile?.accountInfo?.email ?: ""

    var socketState: SocketState? = null

    val transactions = mutableListOf<TransactionBrief>()
}