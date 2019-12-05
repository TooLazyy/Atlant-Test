package com.example.atlanttest.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.example.atlanttest.R
import com.example.atlanttest.presentation.base.LoadingState

class LoadingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val bgColor = ContextCompat.getColor(context, R.color.white_50)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_loading, this)
        setOnClickListener {  }
        ViewCompat.setElevation(this, 10F)
    }

    fun render(loadingState: LoadingState) {
        when (loadingState) {
            is LoadingState.None -> isVisible = false
            else -> {
                isVisible = true
                setBackgroundColor(bgColor)
            }
        }
    }
}