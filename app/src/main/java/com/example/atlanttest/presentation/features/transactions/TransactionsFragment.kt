package com.example.atlanttest.presentation.features.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atlanttest.R
import com.example.atlanttest.extensions.EventObserver
import com.example.atlanttest.extensions.toast
import kotlinx.android.synthetic.main.fragment_transactions.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class TransactionsFragment : Fragment(R.layout.fragment_transactions) {

    companion object {

        const val TAG = "TransactionsFragment"
    }

    private val vm: TransactionsVM by viewModel()
    private val adapter = EasyAdapter()
    private val itemController = TransactionController()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToState()
        initListeners()
        initRecycler()
        vm.loadData()
    }

    private fun subscribeToState() {
        vm.stateData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                render(it)
            }
        })
        vm.errorData.observe(viewLifecycleOwner, EventObserver {
            toast(R.string.error_common)
        })
    }

    private fun render(state: TransactionsState) {
        v_loading.render(state.loading)
        if (state.hasProfileInfo) {
            tv_profile_info.text = getString(
                R.string.trans_profile_info,
                state.firstName,
                state.lastName,
                state.email
            )
        }
        state.socketState?.let {
            tv_socket_state.text = getString(
                R.string.trans_socket_state,
                it.toString()
            )
        }
        adapter.setItems(
            ItemList
                .create()
                .addAll(state.transactions, itemController)
        )
        rv_transactions.scrollToPosition(adapter.itemCount - 1)
    }

    private fun initListeners() {
        iv_exit.setOnClickListener { vm.onExitClick() }
        btn_dispose.setOnClickListener {
            vm.onDisposeClicked()
        }
        btn_stop.setOnClickListener {
            vm.onStopClicked()
        }
        btn_start.setOnClickListener {
            vm.onStartClicked()
        }
    }

    private fun initRecycler() {
        rv_transactions.run {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = this@TransactionsFragment.adapter
        }
    }
}