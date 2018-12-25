package com.lqd.commonimp.extend

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

inline infix fun <T> Deferred<T>.then(crossinline block: (T) -> Unit) {
    GlobalScope.launch(context = Dispatchers.Main) {
        block(this@then.await())
    }
}