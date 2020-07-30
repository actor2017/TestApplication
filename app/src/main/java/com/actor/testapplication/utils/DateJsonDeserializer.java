package com.actor.testapplication.utils;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;

/**
 * description: 类的描述
 *
 * @author : 李大发
 * date       : 2020/7/30 on 19:09
 * @version 1.0
 */
//public class DateJsonDeserializer implements TypeAdapterFactory {
//public class DateJsonDeserializer implements JsonDeserializer<Date> {
public class DateJsonDeserializer extends TypeAdapter<Date> {

    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked") // we use a runtime check to make sure the 'T's equal
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new DateJsonDeserializer() : null;
        }
    };

//    @Override
//    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
//        Class<? super T> rawType = typeToken.getRawType();
//        Type type1 = typeToken.getType();
//
//        Annotation[] annotations = rawType.getAnnotations();
//
//        Annotation[] annotations1 = type1.getClass().getAnnotations();
//
//        return typeToken.getRawType() == Date.class ? (TypeAdapter<T>) new DateTypeAdapter() : null;
//        return null;
//    }



//    @Override
//    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
//        String value = json.getAsString();
//
////        Date d = ReflectUtils.reflect(typeOfT).get();
//
//        if (value != null && !value.isEmpty()) {
////            GsonField gsonField = typeOfT.getClass().getDeclaredAnnotation(GsonField.class);
//            GsonField gsonField = typeOfT.getClass().getAnnotation(GsonField.class);
//            if (gsonField != null) {
//                String format = gsonField.dateSeriseFormat();
//                Date date = TimeUtils.string2Date(value, format);
//                return date;
//            }
//        }
//
////        Date date = context.deserialize(json, typeOfT);
//        return null;
//    }


    @Override
    public void write(JsonWriter out, Date value) throws IOException {
    }

    @Override
    public Date read(JsonReader in) throws IOException {
        return null;
    }
}
