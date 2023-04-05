package com.actor.testapplication.utils.okhttp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.myandroidframework.utils.LogUtils;
import com.actor.myandroidframework.utils.TextUtils2;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Description:
 * 返回基类, 主要处理错误事件 & 解析成实体类 & 或者泛型里什么都不传,会直接返回Response
 *
 * 在{@link #onBefore(int)} 的时候, 默认会显示LoadingDialog, 可重写此方法.
 * 在{@link #onError(int, Call, Exception)} 的时候, 默认会隐藏LoadingDialog, 可重写此方法.
 *
 * Date       : 2020/10/25 on 17:41
 * @version 1.2 重写onResponse, 增加okOk
 * @version 1.3 增加onParseNetworkResponseIsNull
 * @version 1.4 把tag等修改成public, 外界可以获取
 * @version 1.4.1 修改Format错误导致崩溃问题 & 修改取消请求后, onError崩溃问题(增加call.isCanceled()判断)
 * @version 1.4.3 修改错误线程问题, 添加ThreadUtils.runOnUiThread
 */
public abstract class BaseCallback<T> implements okhttp3.Callback {

    protected boolean           isStatusCodeError            = false;//状态码错误
    protected boolean           isParseNetworkResponseIsNull = false;//解析成的实体entity=null
    protected boolean           isJsonParseException         = false;//Json解析异常
    public    Object            tag;
    public    int               id;
    public    boolean           thisRequestIsRefresh         = false;//这次请求是否是(下拉)刷新

    public BaseCallback(@Nullable Object tag) {
        this.tag = tag;
        onBefore(id);
    }

    /**
     * @param tag 2个作用:
     *            1.1.传入Activity(继承ActorBaseActivity)/Fragment(继承ActorBaseFragment), 用于销毁的时候取消请求.
     *            1.2.如果是在Dialog/Others..., 需要自己调用: {@link OkHttpUtils#cancelTag(Object)}
     * @param id  1.可传入"List/RecyclerView"的position或item对应的id,
     *              当你在List/RecyclerView中多个item"同时请求"时, 这个id可用于区别你这次请求是哪一个item发起的.
     *            2.也可用于需要"同时上传"多个文件, 但每次只能上传一个文件的情况. 传入文件对应的position,
     *              当上传成功后, 就可根据这个id判断是上传哪一个文件.
     */
    public BaseCallback(@Nullable Object tag, int id) {
        this.tag = tag;
        this.id = id;
        onBefore(id);
    }

    /**
     * @param isRefresh 下拉刷新 or 上拉加载, 可用于列表请求时, 标记这次请求
     */
    public BaseCallback(@Nullable Object tag, boolean isRefresh) {
        this.tag = tag;
        this.thisRequestIsRefresh = isRefresh;
        onBefore(id);
    }

    /**
     * 开始请求前.
     */
    public void onBefore(int id) {//111
    }

    /**
     * 返回数据, 子线程
     */
    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (response.isSuccessful()) {
            T t = parseNetworkResponse(response, id);
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (t != null) {
                        onOk(t, id);
                    } else {
                        isParseNetworkResponseIsNull = true;
                        if (!isJsonParseException) {//如果不是Json解析错误的原因, 而是其它原因
                            onParseNetworkResponseIsNull(response, id);
                            onError(id, null, null);//主要作用是调用子类的onError方法
                        }
                    }
                }
            });
        } else {
            isStatusCodeError = true;
            ThreadUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    onStatusCodeError(response.code(), response, id);
                }
            });
            onFailure(call, new IOException("状态码错误: " + response.code()));
        }
    }

    /**
     * 解析数据, 子线程
     */
    protected T parseNetworkResponse(Response response, int id) throws IOException {
        if (response == null) return null;
        Type genericity = getGenericityType(this);
        if (genericity == Response.class || genericity == Object.class) {
            return (T) response;
        }
        ResponseBody body = response.body();
        if (body == null) return null;
        String json = body.string();
        if (genericity == String.class) {
            return (T) json;
        } else {//解析成: JSONObject & JSONArray & T
            try {
                /**
                 * Gson: 数据类型不对(""解析成int) & 非json类型数据, 默认都会抛异常
                 * @see com.actor.myandroidframework.utils.gson.IntJsonDeserializer
                 */
                return GsonUtils.fromJson(json, genericity);
                //FastJson: bug太多也不修复一下, 删掉...
//                return JSONObject.parseObject(json, genericity);
            } catch (Exception e) {
                e.printStackTrace();
                isJsonParseException = true;
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onJsonParseException(response, id, e);
                        onError(id, null, e);//主要作用是调用子类的onError方法
                    }
                });
                return null;
            }
        }
    }

    /**
     * 请求成功回调, 主线程
     */
    public abstract void onOk(@NonNull T info, int id);

    /**
     * 失败 or 状态码错误, 子线程
     */
    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        LogUtils.formatError("onError: call=%s, e=%s, id=%d", call, e, id);
        if (call == null || call.isCanceled() || e == null) return;
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onError(id, call, e);
            }
        });
    }

    //不能重写上面那个方法, 要重写就重写这个
    public void onError(int id, Call call, Exception e) {
        if (isStatusCodeError || isJsonParseException || isParseNetworkResponseIsNull) return;
        if (e instanceof SocketTimeoutException) {
            ToastUtils.showShort("连接服务器超时!");
        } else if (e instanceof ConnectException) {
            ToastUtils.showShort("网络连接失败!");
        } else if (e != null) {
            String message = e.getMessage();
            if (message != null) ToastUtils.showShort("错误信息: ".concat(message));
        }
    }

    /**
     * 状态码错误, 默认会toast, 可以重写本方法
     *
     * @param errCode 错误码
     */
    public void onStatusCodeError(int errCode, Response response, int id) {
        String s = TextUtils2.getStringFormat("状态码错误: errCode=%d, response=%s, id=%d", errCode, response, id);
        LogUtils.error(s);
        ToastUtils.showShort(TextUtils2.getStringFormat("状态码错误: %d", errCode));
    }

    /**
     * 数据解析错误, 默认会toast, 可重写此方法
     */
    public void onJsonParseException(Response response, int id, Exception e) {
        String s = TextUtils2.getStringFormat("数据解析错误: response=%s, id=%d, e=%s", response, id, e);
        LogUtils.error(s);
        ToastUtils.showShort("数据解析错误");
    }

    /**
     * 数据解析为空, 默认会toast, 可重写此方法
     */
    public void onParseNetworkResponseIsNull(Response response, int id) {
        LogUtils.formatError("数据解析为空: tag=%s, response=%s, id=%d", tag, response, id);
        ToastUtils.showShort("数据解析为空");
    }

    protected Type getGenericityType(Object object) {
        Type type = object.getClass().getGenericSuperclass();
        return ((ParameterizedType) type).getActualTypeArguments()[0];
    }
}
