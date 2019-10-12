package com.actor.testapplication.activity;

import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.VideoView;

import com.actor.testapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.video_view)
    VideoView      videoView;

    private String url = "http://tb-video.bdstatic.com/tieba-smallvideo-transcode/8_4871b1e9218ec13f03131176197ef53d_1.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        videoView.setVideoURI(Uri.parse(url));
    }

    @OnClick({R.id.btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                videoView.start();
                break;
        }
    }
}
