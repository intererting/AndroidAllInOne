package com.lqd.commonimp.extend

import com.google.gson.Gson

inline fun <reified T> Gson.deserialize(str: String): T {
    return this.fromJson(str, T::class.java)
}
