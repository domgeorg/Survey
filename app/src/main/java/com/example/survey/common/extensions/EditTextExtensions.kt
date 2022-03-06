package com.example.survey.common.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}