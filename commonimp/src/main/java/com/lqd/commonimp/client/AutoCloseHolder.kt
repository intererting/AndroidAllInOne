package com.lqd.commonimp.client

import java.lang.Exception

class ResourceHolder : AutoCloseable {
    private val resources = arrayListOf<AutoCloseable>()
    fun <T : AutoCloseable> T.autoClose(): T {
        resources.add(this)
        return this
    }

    override fun close() {
        resources.reverse()
        resources.forEach { it.close() }
    }
}

fun <R> tryWithResource(block: ResourceHolder.() -> R): R? {
    val holder = ResourceHolder()
    try {
        return holder.block()
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    } finally {
        holder.close()
    }
}