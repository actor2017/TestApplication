package com.actor.testapplication.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.actor.testapplication.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：win
 * 时间：2019/7/24
 * 注释：
 */
public class AudioPlayerLaout extends LinearLayout implements View.OnClickListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnBufferingUpdateListener,
        SeekBar.OnSeekBarChangeListener {

    private SeekBar              seekBar;
    private ImageView            ivPlay;
    private TextView             tvCurrentTime;
    private TextView             tvTotalTime;
    private MediaPlayer          player;
    private Timer                timer = new Timer();
    private OnCloseClickListener listener;

    public AudioPlayerLaout(Context context) {
        super(context);
        initViews();
    }


    public AudioPlayerLaout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public AudioPlayerLaout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews();
    }

    private void initViews() {
        inflate(getContext(), R.layout.audio_player_layout, this);
        seekBar = findViewById(R.id.seek_bar);
        ivPlay = findViewById(R.id.iv_play);
        ImageView ivClose = findViewById(R.id.iv_close);
        tvCurrentTime = findViewById(R.id.tv_current_time);
        tvTotalTime = findViewById(R.id.tv_total_time);
        ivPlay.setEnabled(false);
        seekBar.setEnabled(false);
        ivPlay.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(this);
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        player.setOnPreparedListener(this);
        player.setOnBufferingUpdateListener(this);
        timer.schedule(timerTask, 0, 1000);
    }

    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (player == null) {
                return;
            }
            if (player.isPlaying() && seekBar.isPressed() == false) {
                handler.sendEmptyMessage(0); // 发送消息
            }
        }
    };
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            int position = player.getCurrentPosition();
            int duration = player.getDuration();
            if (duration > 0) {
                // 计算进度（获取进度条最大刻度*当前音乐播放位置 / 当前音乐时长）
                long pos = seekBar.getMax() * position / duration;
                seekBar.setProgress((int) pos);
                tvCurrentTime.setText(timeParse(position));
            }
        }
    };

    public void playUrl(String url) {
        try {
            player.reset();
            player.setDataSource(url);
            player.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_play:
                if (ivPlay.isSelected()) {
                    pause();
                } else {
                    play();
                }
                break;
            case R.id.iv_close:
                if (listener != null) {
                    listener.onCloseClick();
                }
                break;
            default:
                break;
        }
    }

    private void play() {
        if (!player.isPlaying()) {
            player.start();
            ivPlay.setSelected(true);
        }
    }

    private void pause() {
        if (player.isPlaying()) {
            player.pause();
            ivPlay.setSelected(false);
        }
    }

    public void release() {
        if (player != null) {
            timer.cancel();
            player.stop();
            player.release();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        ivPlay.setSelected(false);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        ivPlay.setEnabled(true);
        seekBar.setEnabled(true);
        int duration = player.getDuration();
        tvTotalTime.setText(timeParse(duration));
    }

    private String timeParse(long duration) {
        String time = "";
        long minute = duration / 60000;
        long seconds = duration % 60000;
        long second = Math.round((float) seconds / 1000);
        if (minute < 10) {
            time += "0";
        }
        time += minute + ":";
        if (second < 10) {
            time += "0";
        }
        time += second;
        return time;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        seekBar.setSecondaryProgress(percent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            int max = seekBar.getMax();
            int duration = player.getDuration();
            int i = duration / max;
            int seekProgress = i * progress;
            player.seekTo(seekProgress);
            play();
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public interface OnCloseClickListener {
        void onCloseClick();
    }

    public void setOnCloseClickListener(OnCloseClickListener listener) {
        this.listener = listener;
    }
}
