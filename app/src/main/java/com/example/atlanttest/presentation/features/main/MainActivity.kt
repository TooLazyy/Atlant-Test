package com.example.atlanttest.presentation.features.main

import android.os.Bundle
import com.example.atlanttest.R
import com.example.atlanttest.presentation.base.BaseNavigatorActivity
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseNavigatorActivity() {

    private val vm: MainVM by viewModel()

    override val containerId: Int = R.id.f_content

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            vm.openInitialScreen()
        }
    }
}
