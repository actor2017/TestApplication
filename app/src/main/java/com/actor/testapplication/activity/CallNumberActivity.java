package com.actor.testapplication.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.CheckBox;

import com.actor.myandroidframework.utils.SPUtils;
import com.actor.myandroidframework.widget.ItemTextInputLayout;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.ActivityCallNumberBinding;
import com.actor.testapplication.utils.Global;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * description: 打电话
 * company    :
 *
 * @author : ldf
 * date       : 2022/9/15/0015 on 10:43
 */
// TODO: 2023/4/5 定时打电话未完成
public class CallNumberActivity extends BaseActivity<ActivityCallNumberBinding> {

    private ItemTextInputLayout itilCall, itilBeCall;
    private CheckBox cbCallForever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itilCall = viewBinding.itilCall;
        itilBeCall = viewBinding.itilBeCall;
        cbCallForever = viewBinding.cbCallForever;
        setOnClickListeners(R.id.btn_start, R.id.btn_stop);

        itilCall.setText(SPUtils.getString(Global.CALL_NUMBER));
        itilBeCall.setText(SPUtils.getString(Global.BE_CALL_NUMBER));

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //监听电话状态
        tm.listen(new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String phoneNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_IDLE:
                        //当挂断电话时，发送一个延时消息，调用拨打电话的逻辑，实现循环拨打电话
//                    mHandler.sendEmptyMessageDelayed(0, 3000);
                        cbCallForever.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                callPhone();
                            }
                        }, 500L);
                        break;
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                        break;
                    case TelephonyManager.CALL_STATE_RINGING:
                        break;
                    default:
                        break;
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onViewClicked(View view) {
        super.onViewClicked(view);
        switch (view.getId()) {
            case R.id.btn_start:
                if (isNoEmpty(itilCall, itilBeCall)) {
                    checkPermission();
                }
                break;
            case R.id.btn_stop:
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        String permission = Manifest.permission.CALL_PHONE;
        boolean granted = XXPermissions.isGranted(this, permission);
        if (!granted) {
            XXPermissions.with(this).permission(permission).request(new OnPermissionCallback() {
                @Override
                public void onGranted(List<String> permissions, boolean all) {
                    callPhone();
                }
            });
        } else {
            callPhone();
        }
    }

    public void callPhone() {
        String phoneNum = getText(itilBeCall);
        Uri data = Uri.parse("tel:" + phoneNum);
        startActivity(new Intent(Intent.ACTION_CALL)
                .setData(data)
        );
    }
}