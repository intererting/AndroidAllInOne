/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lqd.httpclient

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
</T> */
data class Resource<out T>(val status: Status, val data: T?, val msg: String? = null, val code: Int = -1) {
    companion object {

        fun <T> success(data: T?, msg: String?): Resource<T> {
            return Resource(Status.SUCCESS, data, msg)
        }

        fun <T> failed(data: T?, msg: String?, code: Int): Resource<T> {
            return Resource(Status.FAILED, data, msg, code)
        }

        fun <T> loading(data: T?, msg: String?): Resource<T> {
            return Resource(Status.LOADING, data, msg)
        }
    }
}
