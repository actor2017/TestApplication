package com.actor.testapplication.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.actor.myandroidframework.utils.FileUtils;
import com.actor.testapplication.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * SurfaceView
 * FIXME: 2020/2/14 SurfaceView待改进
 */
public class SurfaceViewActivity extends BaseActivity {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;

    private Camera        camera;
    private MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surface_view);
        ButterKnife.bind(this);

        mediaRecorder = new MediaRecorder();//实例化媒体录制器

        //添加surface回调函数
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {

            //控件创建时，打开照相机
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                logError("surfaceCreated");

                camera = Camera.open();//打开照相机
                if (camera == null) return;

                //设置参数
                Camera.Parameters parameters = camera.getParameters();
                parameters.setPictureFormat(ImageFormat.JPEG);
//                parameters.set("jpeg-quality", 85);//↓
                parameters.setJpegQuality(100);
                try {
                    camera.setParameters(parameters);//将画面展示到SurfaceView
                    camera.setPreviewDisplay(holder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                camera.startPreview();//开启预览效果
            }

            //控件改变
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                logFormat("surfaceChanged: holder=%s, format=%d, width=%d, height=%d",
                        holder, format, width, height);
            }

            //控件销毁
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                logError("surfaceDestroyed");
                //照相同一时刻只能允许一个软件打开
                if (camera != null) {
                    camera.stopPreview();
                    camera.release();//释放内存
                    camera = null;
                }
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.btn1://拍照
                takePhoto();
                break;
            case R.id.btn2://开始
                mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置格式    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
                mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
                mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                //设置保存路径
                String path = FileUtils.getExternalStoragePath(System.currentTimeMillis() + ".mp4");
                mediaRecorder.setOutputFile(path);
                mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn3://停止
                if(mediaRecorder!=null){
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder=null;
                }
                break;
        }
    }

    //拍照
    private void takePhoto() {
        camera.takePicture(null, null, new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                //技术：图片压缩技术（如果图片不压缩，图片大小会过大，会报一个oom内存溢出的错误）
                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                try {
                    String path = FileUtils.getExternalStoragePath(System.currentTimeMillis() + ".png");
                    FileOutputStream fos = new FileOutputStream(path);//图片保存路径
                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);//压缩格式，质量，压缩路径
                    camera.stopPreview();
                    camera.startPreview();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
