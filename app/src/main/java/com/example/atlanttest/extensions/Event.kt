package com.example.atlanttest.extensions

import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

class Event<T>(
    private val event: T
) {

    private val isEventConsumed = AtomicBoolean(false)

    val eventValue: T?
        get() {
            return if (isEventConsumed.compareAndSet(false, true)) {
                event
            } else {
                null
            }
        }
}

class EventObserver<T>(
    private val onEventChanged: (T) -> Unit
) : Observer<Event<T>> {

    override fun onChanged(event: Event<T>?) {
        event?.let { value ->
            value.eventValue?.let {
                onEventChanged(it)
            }
        }
    }
}