package com.readygo.yf.base.client

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

class GsonNullStringAdapter : TypeAdapter<String>() {
    override fun write(writer: JsonWriter, value: String?) {
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

    override fun read(reader: JsonReader): String {
        try {
            if (reader.peek() === JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString().toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}