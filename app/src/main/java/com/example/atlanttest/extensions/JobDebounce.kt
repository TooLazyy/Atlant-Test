package com.example.atlanttest.extensions

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class JobDebounce(
    private val debounceTime: Long
) {

    private var job: Job? = null

    fun debounceAction(scope: CoroutineScope, action: () -> Unit) {
        job?.cancel()

        job = scope.launch {
            delay(debounceTime)
            action()
        }
    }
}