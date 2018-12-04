package com.lqd.commonimp.extend

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.lqd.commonimp.client.BaseApplication

/**
 * @name：EditTextExtends
 * @author 李沐阳
 * @date：2018/9/13 17:45
 * @Deprecated
 */

/**
 * Show the soft input.
 *
 * @param view The view.
 */
fun EditText.showKeyboard() {
    val imm = BaseApplication.provideInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    requestFocus()
    imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
}

fun EditText.hideKeyboard() {
    val imm = BaseApplication.provideInstance().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun EditText.isInputBlank(): Boolean {
    return this.text.isNullOrBlank()
}

fun EditText.inputText(): String {
    return this.text.ifEmpty { "" }.trim().toString()
}

fun EditText.onTextChanged(watcher: (s: CharSequence?, start: Int, before: Int, count: Int) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            watcher.invoke(s, start, before, count)
        }
    })
}