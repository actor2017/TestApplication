package com.actor.testapplication.utils.okhttp;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.myandroidframework.utils.ConfigUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.actor.myandroidframework.utils.okhttputils.BaseCallback;
import com.actor.myandroidframework.utils.okhttputils.NullCallback;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * description: OkHttp工具类
 * 使用:
 * 1. OkHttpUtils.getInstance().initClient(okHttpClient);
 *
 * date       : 2020/10/9 on 09
 * @version 1.0
 */
public class OkHttpUtils {

    protected static transient OkHttpClient okHttpClient;

    //Base Url
    protected static final String BASE_URL = ConfigUtils.baseUrl;

    /**
     * 初始化
     */
    public static void initClient(OkHttpClient okHttpClient) {
        OkHttpUtils.okHttpClient = okHttpClient;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) okHttpClient = new OkHttpClient();
        return okHttpClient;
    }

    /**
     * 获取 BASE_URL
     * @param url 如果是"http"开头, 直接返回url.
     *           如果不是"http"开头, 会在前面加上 BASE_URL
     */
    protected static String getUrl(String url) {
        if (url == null) return BASE_URL;
        if (url.startsWith("http://") || url.startsWith("https://")) return url;
        return BASE_URL + url;
    }

    public static <T> void get(String url, Map<String, Object> params, BaseCallback<T> callback) {
        get(url, null, params, callback);
    }

    /**
     * get方式请求数据
     * @param url       地址
     * @param headers   请求头
     * @param params    参数,一般用LinkedHashMap<String, Object>
     * @param callback  回调
     * @param <T>       要解析成什么类型的对象,示例:
     *                      JSONObject, String, List<UserInfo>,
     *                      BaseInfo, BaseInfo<UserInfo>, BaseInfo<List<UserInfo>>, ...
     */
    public static <T> void get(String url, Map<String, Object> headers, Map<String, Object> params, BaseCallback<T> callback) {
        Request.Builder builder = new Request.Builder()
                .get()
                .url(urlAppendParams(getUrl(url), params))
                .tag(callback == null ? null : callback.tag);
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                String key = entry.getKey();
                if (!TextUtils.isEmpty(key)) {
                    builder.addHeader(key, getNoNullString(entry.getValue()));
                }
            }
        }
        getOkHttpClient()
                //.newBuilder().connectTimeout()...
                .newCall(builder.build())
                .enqueue(callback == null ? new NullCallback() : callback);//不能为空
        if (callback != null) callback.onBefore(null, callback.getRequestId());//okhttp3.Callback需要手动调一下...

        /**
         * 不能使用{@link com.zhy.http.okhttp.OkHttpUtils#get()}, 因为↓ 这个方法组合成的url不对
         * @see com.zhy.http.okhttp.builder.GetBuilder#appendParams(String, Map)
         * 会导致请求时url一些不该被Encode的字符被Encode(: => %3A), 例:
         * @see com.actor.myandroidframework.utils.baidu.BaiduMapUtils#getAddressByNet(double, double, BaseCallback)//"报错: APP Mcode码校验失败"
         */
//        OkHttpUtils.get().url(getUrl(url))
//                .tag(callback == null ? null : callback.tag)
//                .headers(cleanNullParamMap(headers))
//                .params(cleanNullParamMap(params))
//                //请求id, 会在回调中返回, 可用于列表请求中传入item的position, 然后在回调中根据id修改对应的item的值
//                .id(callback == null ? 0 : callback.id)
//                .build()
////                .connTimeOut(20000).readTimeOut(20000).writeTimeOut(20000)
//                .execute(callback);
    }


    /**
     * 将params拼接到url后面
     * @param url url
     * @param params 参数
     * @return 拼接后的url
     */
    protected static @NonNull
    String urlAppendParams(@NonNull String url, @Nullable Map<String, Object> params) {
        if (params == null || params.isEmpty()) return url;
        StringBuilder builder = new StringBuilder(url);
        boolean endWihtQuestionMark;//是否'?'结尾
        if (!url.contains("?")) {
            builder.append("?");
            endWihtQuestionMark = true;
        } else {
            endWihtQuestionMark = url.endsWith("?");
        }
        //http://www.xxx.com/?a=a & b=b & c=c
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            if (!TextUtils.isEmpty(key)) {
                String value = getNoNullString(entry.getValue());
                if (endWihtQuestionMark) {
                    builder.append(key).append("=").append(value);
                    endWihtQuestionMark = false;
                } else builder.append("&").append(key).append("=").append(value);
            }
        }
        return builder.toString();
    }

    /**
     * 清除key值为null的参数 & 保证value != null
     * 并且转换为Map<String, String>
     */
    protected static @Nullable Map<String, String> cleanNullParamMap(@Nullable Map<String, Object> map) {
        if (map == null || map.isEmpty()) return null;
        Map<String, String> returnMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            if (!TextUtils.isEmpty(key)) {
                returnMap.put(key, getNoNullString(entry.getValue()));
            }
        }
        return returnMap.isEmpty() ? null : returnMap;
    }

    protected static String getNoNullString(Object object) {
        return TextUtils2.getNoNullString(object);
    }
}
