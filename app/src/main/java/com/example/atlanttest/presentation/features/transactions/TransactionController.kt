package com.example.atlanttest.presentation.features.transactions

import android.view.ViewGroup
import com.example.atlanttest.R
import com.example.atlanttest.extensions.DateTimeUtils
import com.example.domain.TransactionBrief
import kotlinx.android.synthetic.main.item_transaction.view.*
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class TransactionController :
    BindableItemController<TransactionBrief, TransactionController.TransactionHolder>() {

    override fun getItemId(item: TransactionBrief): String =
        item.hash

    override fun createViewHolder(parent: ViewGroup?): TransactionHolder =
        TransactionHolder(parent)

    inner class TransactionHolder(
        parent: ViewGroup?
    ) : BindableViewHolder<TransactionBrief>(parent, R.layout.item_transaction) {

        override fun bind(item: TransactionBrief) {
            itemView.tv_date.text = DateTimeUtils.formatTransactionTimestamp(item.timestamp)
            itemView.tv_hash.text = item.hash
        }
    }
}