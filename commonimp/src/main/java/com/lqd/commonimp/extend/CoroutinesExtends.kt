package com.lqd.commonimp.extend

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*

fun <T> Deferred<T>.then(block: (T) -> Unit, owner: LifecycleOwner): Job {

    return (owner as CoroutineScope).launch(context = Dispatchers.Main) {
        if (owner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
            block(this@then.await())
        }
    }
}