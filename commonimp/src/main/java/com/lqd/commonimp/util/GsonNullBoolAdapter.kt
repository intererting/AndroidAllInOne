package com.readygo.yf.base.client

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class GsonNullBoolAdapter : TypeAdapter<Boolean>() {
    override fun write(writer: JsonWriter, value: Boolean?) {
        try {
            if (value == null) {
                writer.nullValue()
                return
            }
            writer.value(value)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun read(reader: JsonReader): Boolean {
        try {
            if (reader.peek() === JsonToken.NULL) {
                reader.nextNull()
                return false
            }
            return reader.nextBoolean()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}