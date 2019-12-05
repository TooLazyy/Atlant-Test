package com.example.atlanttest.presentation.features.auth

import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.atlanttest.R
import com.example.atlanttest.extensions.EventObserver
import com.example.atlanttest.extensions.toast
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment : Fragment(R.layout.fragment_auth) {

    companion object {

        const val TAG = "AuthFragment"
    }

    private val vm: AuthVM by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        subscribeToState()
        initListeners()
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

    private fun render(state: AuthState) {
        v_loading.render(state.loading)
        btn_login.isEnabled = state.isLoginEnabled
    }

    private fun initListeners() {
        et_email.doAfterTextChanged { vm.onEmailChanged(it?.toString() ?: "") }
        et_password.doAfterTextChanged { vm.onPasswordChanged(it?.toString() ?: "") }
        btn_login.setOnClickListener { vm.onLoginClick() }

        et_email.setText("hello@karta.com")
        et_password.setText("12345678")
    }
}