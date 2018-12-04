package com.lqd.httpclient;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

import static com.lqd.httpclient.ConstantKt.NET_INFO_FAILED;
import static com.lqd.httpclient.ConstantKt.NET_INFO_SUCCESS;

public class MyGsonConverterFactory extends Converter.Factory {

    /**
     * Create an instance using a default {@link Gson} instance for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public static MyGsonConverterFactory create() {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(String.class, new GsonNullStringAdapter());
        gsonBuilder.registerTypeAdapter(Boolean.class, new GsonNullBoolAdapter()).create();
        return create(gsonBuilder.create());
    }

    /**
     * Create an instance using {@code gson} for conversion. Encoding to JSON and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    private static MyGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new MyGsonConverterFactory(gson);
    }

    private final Gson gson;

    private MyGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    static final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final Gson           gson;
        private final TypeAdapter<T> adapter;

        GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            String response = value.string();
            JsonReader jsonReader;
            try {
                JSONObject object = new JSONObject(response);
                String resultCode = object.getString("resultCode");
                if (!resultCode.equals(NET_INFO_SUCCESS)) {
                    ResponseEntity responseEntity = new ResponseEntity();
                    responseEntity.setResultMsg(object.getString("resultMsg"));
                    responseEntity.setResultCount("0");
                    responseEntity.setResultCode(NET_INFO_FAILED);
                    return (T) responseEntity;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                MediaType mediaType = value.contentType();
                Charset charset = mediaType != null ? mediaType.charset(Charset.forName("utf-8")) : Charset.forName("utf-8");
                InputStream inputStream = new ByteArrayInputStream(response.getBytes());
                jsonReader = gson.newJsonReader(new InputStreamReader(inputStream, charset));
            }
            try {
                return adapter.read(jsonReader);
            } finally {
                value.close();
            }
        }
    }

    static final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
        private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
        private static final Charset   UTF_8      = Charset.forName("UTF-8");

        private final Gson           gson;
        private final TypeAdapter<T> adapter;

        GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
            this.gson = gson;
            this.adapter = adapter;
        }

        @Override
        public RequestBody convert(T value) throws IOException {
            Buffer buffer = new Buffer();
            Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
            JsonWriter jsonWriter = gson.newJsonWriter(writer);
            adapter.write(jsonWriter, value);
            jsonWriter.close();
            return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
        }
    }

}
