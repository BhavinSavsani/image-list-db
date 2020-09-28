package com.bhavinpracticalcavista.utils

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bhavinpracticalcavista.R
import com.google.android.material.snackbar.Snackbar

object SnackBarHelper {

    fun showError(rootView: View, message: String?) {
        with(
            Snackbar
                .make(rootView, message ?: "Something went wrong", Snackbar.LENGTH_LONG)
        ) {
            this.setAction("Ok") {
                dismiss()
            }
            this.setActionTextColor(Color.WHITE)
            this.view.apply {
                setBackgroundColor(ContextCompat.getColor(rootView.context, R.color.red))
            }
            this.view.findViewById<TextView>(R.id.snackbar_text)
                .apply {
                    // Set the left icon of message.
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            rootView.context,
                            R.drawable.ic_baseline_close_24
                        ), null, null, null
                    )
                    // Set the padding between message and icon.
                    compoundDrawablePadding = 16
                    // To make icon and message aligned.
                    gravity = Gravity.CENTER
                    // Change color of message text.
                    setTextColor(Color.WHITE)
                }
            this
        }.show()
    }


    fun showWarning(rootView: View, message: String?) {
        with(
            Snackbar
                .make(rootView, message!! , Snackbar.LENGTH_LONG)
        ) {
            this.view.apply {
                setBackgroundColor(ContextCompat.getColor(rootView.context, R.color.warning_color))
            }
            this.view.findViewById<TextView>(R.id.snackbar_text)
                .apply {
                    // Set the left icon of message.
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            rootView.context,
                            R.drawable.ic_baseline_close_24
                        ), null, null, null
                    )
                    // Set the padding between message and icon.
                    compoundDrawablePadding = 16
                    // To make icon and message aligned.
                    gravity = Gravity.CENTER
                    // Change color of message text.
                    setTextColor(Color.WHITE)
                }
            this
        }.show()
    }

    fun showSuccess(rootView: View, message: String) {
        with(
            Snackbar
                .make(rootView, message, Snackbar.LENGTH_SHORT)
        ) {
            this.view.apply {
                setBackgroundColor(ContextCompat.getColor(rootView.context, R.color.green))
            }
            this.view.findViewById<TextView>(R.id.snackbar_text)
                .apply {
                    // Set the left icon of message.
                    setCompoundDrawablesWithIntrinsicBounds(
                        ContextCompat.getDrawable(
                            rootView.context,
                            R.drawable.ic_baseline_check_24
                        ), null, null, null
                    )
                    // Set the padding between message and icon.
                    compoundDrawablePadding = 16
                    // To make icon and message aligned.
                    gravity = Gravity.CENTER
                    // Change color of message text.
                    setTextColor(Color.WHITE)
                }
            this
        }.show()
    }
}