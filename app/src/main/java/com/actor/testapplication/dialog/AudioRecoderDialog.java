package com.actor.testapplication.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.dialog.BaseDialog;
import com.actor.myandroidframework.utils.audio.AudioUtils;
import com.actor.myandroidframework.widget.chat.VoiceRecorderView;
import com.actor.testapplication.R;
import com.actor.testapplication.databinding.DialogAudioRecoderBinding;
import com.blankj.utilcode.util.ToastUtils;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * description: 录音Dialog
 * date       : 2020/4/1 on 17:18
 *
 * @version 1.0
 */
public class AudioRecoderDialog extends BaseDialog implements View.OnClickListener {

    private VoiceRecorderView voiceRecoder;
    private Button            btnStart;

    private boolean               hasPermission;//是否有录音权限
    private boolean                 audioRecordIsCancel;//语音录制是否已取消
    private float                   startRecordY;//按下时的y坐标
    private OnListener onListener;

    public AudioRecoderDialog(@NonNull Context context, OnListener listener) {
        super(context);
        this.onListener = listener;
        setCancelAble(true);
    }

    @Override
    protected int getLayoutResId() {
//        return R.layout.dialog_audio_recoder;
        DialogAudioRecoderBinding inflate = DialogAudioRecoderBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());
        voiceRecoder = inflate.voiceRecoder;
        btnStart = inflate.btnStart;
        inflate.btnCancel.setOnClickListener(this);
        inflate.btnOk.setOnClickListener(this);
        inflate.btnStart.setOnClickListener(this);
        return 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        voiceRecoder = findViewById(R.id.voice_recoder);
//        btnStart = findViewById(R.id.btn_start);
//        findViewById(R.id.btn_cancel).setOnClickListener(this);
//        findViewById(R.id.btn_ok).setOnClickListener(this);
//        findViewById(R.id.btn_start).setOnClickListener(this);
        checkPermission();
        //语音按钮
        btnStart.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.onTouchEvent(event);
                if (!hasPermission) {
                    checkPermission();
                    ToastUtils.showShort("未获取到录音权限!");
                    return false;
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        audioRecordIsCancel = false;
                        startRecordY = event.getY();
                        voiceRecoder.startRecording();
                        AudioUtils.getInstance().startRecord(new AudioUtils.AudioRecordCallback() {

                            @Override
                            public void recordComplete(String audioPath, long durationMs) {
                                if (audioRecordIsCancel) {
                                    voiceRecoder.stopRecording(View.VISIBLE);
                                    return;
                                }
                                if (durationMs < 500) {
                                    voiceRecoder.tooShortRecording();
                                    return;
                                }
                                voiceRecoder.stopRecording(View.VISIBLE);
                                //语音路径
                                String recordAudioPath = AudioUtils.getInstance().getRecordAudioPath();
                                if (!TextUtils.isEmpty(recordAudioPath)) {
                                    onListener.onVoiceRecordSuccess(recordAudioPath, durationMs);
                                }
                                dismiss();
                            }

                            @Override
                            public void recordCancel(String audioPath, long durationMs) {
                                voiceRecoder.stopRecording(View.VISIBLE);
                            }

                            @Override
                            public void recordError(Exception e) {//子线程
                                voiceRecoder.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (voiceRecoder != null) {
                                            voiceRecoder.stopRecording(View.VISIBLE);
                                            onListener.onVoiceRecordError(e);
                                        }
                                    }
                                });
                            }
                        });
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (event.getY() - startRecordY < -100) {
                            audioRecordIsCancel = true;
                            voiceRecoder.release2CancelRecording();//松开手指取消发送
                        } else {
                            //如果release2CancelRecording(), 再重新startRecording(), 否则会一直调startRecording().
                            if (audioRecordIsCancel) {
                                audioRecordIsCancel = false;
                                voiceRecoder.startRecording();//开始录音
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
//                                    if (event.getY() - startRecordY < -100) {
//                                        audioRecordIsCancel = true;
//                                    } else {
//                                        audioRecordIsCancel = false;
//                                    }
                        AudioUtils.getInstance().stopRecord(audioRecordIsCancel);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //当点击外侧/返回键
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                voiceRecoder.stopRecording(View.VISIBLE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel://取消
                dismiss();
                break;
            case R.id.btn_ok://完成
                dismiss();
                break;
            default:
                break;
        }
    }

    private void checkPermission() {
        String recordAudio = Permission.RECORD_AUDIO;
        boolean has = XXPermissions.isGranted(getContext(), recordAudio);
        if (!has) {
            XXPermissions.with(getContext()).permission(recordAudio).request(new OnPermissionCallback() {
                @Override
                public void onGranted(List<String> permissions, boolean all) {
                    hasPermission = true;
                }
                @Override
                public void onDenied(List<String> permissions, boolean never) {
                    ToastUtils.showShort("未获取到录音权限!");
                }
            });
        } else {
            hasPermission = true;
        }
    }

    public interface OnListener {
        /**
         * 语音录制完成
         * @param audioPath 语音路径, 已判空
         * @param durationMs 语音时长, 单位ms
         */
        void onVoiceRecordSuccess(@NonNull String audioPath, long durationMs);

        /**
         * 录音失败
         */
        default void onVoiceRecordError(Exception e) {
            ToastUtils.showShort("录音失败: " + e.getMessage());
        }
    }
}
