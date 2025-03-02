package com.example.simplecomposeproject.RateCoverter.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


object Utility {

    fun hideKeyBoard(context: Context, editText: EditText) {
        try {
            editText.clearFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        } catch (ignored: Exception) {
        }
    }



}
