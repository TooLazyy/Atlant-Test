package com.example.atlanttest.extensions

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

fun Fragment.toast(@StringRes textResId: Int, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(requireContext(), textResId, duration).show()
}