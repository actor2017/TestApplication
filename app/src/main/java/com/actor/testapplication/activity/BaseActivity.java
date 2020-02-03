package com.actor.testapplication.activity;

import com.actor.myandroidframework.activity.ActorBaseActivity;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Description: 类的描述
 * Company    : 重庆市了赢科技有限公司 http://www.liaoin.com/
 * Author     : 李大发
 * Date       : 2019-9-17 on 15:13
 *
 * @version 1.0
 */
public class BaseActivity extends ActorBaseActivity {

        // TODO: 2020-1-21
        //RxEasyHttp

        //第2个页面图片
        //Glide.with(mContext).load(v.picture)
        //                    .apply(new RequestOptions()
        //                    .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL))
        //                    .into(evaluatePic);

    //MediaType = null
    public void register(String registrationID) {
        params.clear();
        params.put("registrationID", registrationID);
        //MediaType = null
        RequestBody requestBody = RequestBody.create(null, JSONObject.toJSONString(params));
        Request request = new Request.Builder()
                .method("POST",requestBody)
                .url("http://120.52.31.133:8081/zhjg/jpush/register" + "?registrationID="+registrationID)
                .build();
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()) {
//                    ResponseBody body = response.body();
//                    Log.e("-----", "onResponse: " + body.string());
//                }
            }
        });
    }

    //delete
    /**
     * 取消预订夜宵
     */
    public void midnightDishDelete(String canteenId) {
        params.clear();
        params.put("canteenId", canteenId);
        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(mediaType, JSONObject.toJSONString(params));
        Request request = new Request.Builder()
                .url("http://120.52.31.133:8081/zhjg/api/ministry/supper/midnightDish/delete")
                .delete(requestBody)
                .build();
        OkHttpClient client = OkHttpUtils.getInstance().getOkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String resJson = response.body().string();
                }
            }
        });
    }
}
