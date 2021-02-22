package com.actor.testapplication.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.actor.myandroidframework.dialog.BaseAlertDialogV7;
import com.actor.testapplication.widget.AudioPlayerLaout;

/**
 * description: 音频播放
 * @author : 李大发
 * date       : 2020/6/19 on 11:15
 * @version 1.0
 */
public class AudioPlayerDialog extends BaseAlertDialogV7 {

    private String playUrl;

    public AudioPlayerDialog(@NonNull Context context, String playUrl) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        this.playUrl = playUrl;
    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioPlayerLaout audioPlayer = new AudioPlayerLaout(getContext());
        setContentView(audioPlayer);

        if (playUrl != null) {
            audioPlayer.playUrl(playUrl);
        }
        audioPlayer.setOnCloseClickListener(this::dismiss);
        setOnDismissListener(dialog -> audioPlayer.release());
    }
}
