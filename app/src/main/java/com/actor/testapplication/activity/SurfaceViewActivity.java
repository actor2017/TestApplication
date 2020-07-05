package com.actor.testapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actor.base.BaseActivity;
import com.actor.myandroidframework.utils.FileUtils;
import com.actor.testapplication.R;
import com.actor.testapplication.utils.CameraParamUtil;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * SurfaceView, {@link com.yanzhenjie.album.app.camera.CameraActivity#onPermissionGranted(int)}
 * FIXME: 2020/2/14 SurfaceView待改进
 */
public class SurfaceViewActivity extends BaseActivity {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.tv_angle)
    TextView       tvAngle;
    @BindView(R.id.ll_angle)
    LinearLayout   llAngle;
    @BindView(R.id.tv_distance)
    TextView       tvDistance;
    @BindView(R.id.tv_direction)
    TextView       tvDirection;
    @BindView(R.id.iv_take_photo)
    ImageView      ivTakePhoto;
    @BindView(R.id.switch_camera)
    ImageView      switchCamera;
    @BindView(R.id.ll_take_photo)
    RelativeLayout llTakePhoto;
    @BindView(R.id.iv_true)
    ImageView      ivTrue;
    @BindView(R.id.iv_false)
    ImageView      ivFalse;
    @BindView(R.id.ll_confirm)
    LinearLayout   llConfirm;

    private SurfaceHolder holder;
    private Camera        camera;//相机
    private int           cameraPosition = 1;//0代表前置摄像头，1代表后置摄像头
    private SensorManager sensorManager;
    private byte[]        photoData;
    private String        direction;
    private String        angle;
    private String        distance;

    private MediaRecorder mediaRecorder;

    public static final String RESULT_PATH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置屏幕朝向,在setContentView之前
        setContentView(R.layout.activity_surface_view);
        ButterKnife.bind(this);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(sensorEventListener2, sensor, SensorManager.SENSOR_DELAY_UI);

        mediaRecorder = new MediaRecorder();//实例化媒体录制器

        holder = surfaceView.getHolder();
    }
    private SensorEventListener2 sensorEventListener2 = new SensorEventListener2() {
        @Override
        public void onFlushCompleted(Sensor sensor) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float value = event.values[0];
            setAngle(value);
            float degree = value + 90;
            if (degree >= 360) {
                degree = degree - 360;
            }
            tvAngle.setText((int) degree + "°");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    private void setAngle(float val) {
        String text = null;
        if (val <= 10 || val >= 350) {
            text = "东";
        } else if (val > 10 && val <= 80) {
            text = "东南";
        } else if (val > 80 && val <= 100) {
            text = "南";
        } else if (val > 100 && val <= 170) {
            text = "西南";
        } else if (val > 170 && val <= 190) {
            text = "西";
        } else if (val > 190 && val <= 260) {
            text = "西北";
        } else if (val > 260 && val <= 280) {
            text = "北";
        } else if (val > 280 && val < 350) {
            text = "东北";
        }
        tvDirection.setText(text);
    }

    @OnClick({R.id.surface_view, R.id.iv_take_photo, R.id.switch_camera, R.id.iv_true,
            R.id.iv_false, R.id.btn2, R.id.btn3})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.surface_view:
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                    }
                });
                break;
            case R.id.iv_take_photo://拍照
                takePhoto();
                break;
            case R.id.switch_camera://切换前后摄像头
                int cameraCount = 0;
                Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数

                for (int i = 0; i < cameraCount; i++) {
                    Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
                    if (cameraPosition == 1) {
                        //现在是后置，变更为前置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            cameraPosition = 0;
                            break;
                        }
                    } else {
                        //现在是前置， 变更为后置
                        if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                            camera.stopPreview();//停掉原来摄像头的预览
                            camera.release();//释放资源
                            camera = null;//取消原来摄像头
                            camera = Camera.open(i);//打开当前选中的摄像头
                            try {
                                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            camera.startPreview();//开始预览
                            cameraPosition = 1;
                            break;
                        }
                    }

                }
                break;
            case R.id.iv_true://拍照完成后点击确定
                if (photoData != null) {
                    try {
                        View infoView = LayoutInflater.from(this).inflate(R.layout.photo_info_layout, null);
                        TextView tvDirection = infoView.findViewById(R.id.tv_direction);
                        TextView tvAngle = infoView.findViewById(R.id.tv_angle);
                        TextView tvDistance = infoView.findViewById(R.id.tv_distance);
                        tvDirection.setText(direction);
                        tvAngle.setText(angle);
                        tvDistance.setText(distance);
                        Bitmap viewToBitmap = convertViewToBitmap(infoView);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(photoData, 0, photoData.length);
                        Bitmap waterMaskLeftTop = createWaterMaskLeftTop(this, bitmap, viewToBitmap, 10, 10);
                        //自定义文件保存路径  以拍摄时间区分命名
                        String path = Environment.getExternalStorageDirectory().getPath();
                        //照片保存路径
                        String filepath = path + "/" + System.currentTimeMillis() + ".jpg";
                        File file = new File(filepath);
                        BufferedOutputStream bos = null;
                        bos = new BufferedOutputStream(new FileOutputStream(file));
                        waterMaskLeftTop.compress(Bitmap.CompressFormat.JPEG, 100, bos);//将图片压缩的流里面
                        bos.flush();// 刷新此缓冲区的输出流
                        bos.close();// 关闭此输出流并释放与此流有关的所有系统资源
                        viewToBitmap.recycle();//回收bitmap空间
                        bitmap.recycle();//回收bitmap空间
                        waterMaskLeftTop.recycle();//回收bitmap空间

                        Intent intent = new Intent();
                        intent.putExtra(RESULT_PATH, filepath);
                        setResult(RESULT_OK, intent);
                        onBackPressed();
                    } catch (Exception e) {
                        toast("拍照失败");
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.iv_false://拍照完成点击取消
                llConfirm.setVisibility(View.GONE);
                llTakePhoto.setVisibility(View.VISIBLE);
                camera.startPreview();
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
        camera.autoFocus(new Camera.AutoFocusCallback() {//自动对焦
            @Override
            public void onAutoFocus(boolean success, Camera camera) {
                if (success) {
                    //设置参数，并拍照
                    Camera.Parameters camParams = camera.getParameters();
                    List<Camera.Size> previewSizes = camParams.getSupportedPreviewSizes();
                    List<Camera.Size> pictureSizes = camParams.getSupportedPictureSizes();
                    int screenWidth = ScreenUtils.getScreenWidth();
                    int screenHeight = ScreenUtils.getScreenHeight();
                    float scale = (float) screenWidth / (float) screenHeight;
                    Camera.Size previewSize = CameraParamUtil.getInstance().getPreviewSize(previewSizes, 1200, scale);
                    Camera.Size pictureSize = CameraParamUtil.getInstance().getPictureSize(pictureSizes, 1200, scale);
                    Camera.Parameters params = camera.getParameters();
                    params.setPictureFormat(PixelFormat.JPEG);//图片格式
//                            params.setPreviewSize(previewSize.width, previewSize.height);//图片大小
                    params.setPictureSize(pictureSize.width, pictureSize.height);
                    camera.setParameters(params);//将参数设置到我的camera
                    camera.takePicture(null, null, jpeg);//将拍摄到的照片给自定义的对象
                    direction = tvDirection.getText().toString();
                    angle = tvAngle.getText().toString();
                    distance = tvDistance.getText().toString();
                }
            }
        });
//        camera.takePicture(null, null, new Camera.PictureCallback() {
//
//            @Override
//            public void onPictureTaken(byte[] bytes, Camera camera) {
//                //技术：图片压缩技术（如果图片不压缩，图片大小会过大，会报一个oom内存溢出的错误）
//                Bitmap bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                try {
//                    String path = FileUtils.getExternalStoragePath(System.currentTimeMillis() + ".png");
//                    FileOutputStream fos = new FileOutputStream(path);//图片保存路径
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 85, fos);//压缩格式，质量，压缩路径
//                    camera.stopPreview();
//                    camera.startPreview();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    public Bitmap createWaterMaskLeftTop(
            Context context, Bitmap src, Bitmap watermark,
            int paddingLeft, int paddingTop) {
        return createWaterMaskBitmap(src, watermark,
                ConvertUtils.dp2px(paddingLeft), ConvertUtils.dp2px(paddingTop));
    }
    private static Bitmap createWaterMaskBitmap(Bitmap src, Bitmap watermark,
                                                int paddingLeft, int paddingTop) {
        if (src == null) {
            return null;
        }
        int width = src.getWidth();
        int height = src.getHeight();
        //创建一个bitmap
        Bitmap newb = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        //将该图片作为画布
        Canvas canvas = new Canvas(newb);
        //在画布 0，0坐标上开始绘制原始图片
        canvas.drawBitmap(src, 0, 0, null);
        //在画布上绘制水印图片
        canvas.drawBitmap(watermark, paddingLeft, paddingTop, null);
        // 保存
        canvas.save();
        // 存储
        canvas.restore();
        return newb;
    }
    private Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        return view.getDrawingCache();
    }

    private SurfaceHolder.Callback surfaceHolderCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (camera == null) {
                camera = Camera.open();
//                //设置参数
//                Camera.Parameters parameters = camera.getParameters();
//                parameters.setPictureFormat(ImageFormat.JPEG);
////                parameters.set("jpeg-quality", 85);//↓
//                parameters.setJpegQuality(100);
                try {
//                    camera.setParameters(parameters);//将画面展示到SurfaceView
                    camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                    camera.startPreview();//开始预览
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //SurfaceView 渲染宽高
            logFormat("surfaceChanged: format=%d, width=%d, height=%d", format, width, height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            holder.removeCallback(this);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    };

    Camera.PictureCallback jpeg = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                camera.stopPreview();//关闭预览 处理数据
                llTakePhoto.setVisibility(View.GONE);
                llConfirm.setVisibility(View.VISIBLE);
                photoData = data;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        holder.addCallback(surfaceHolderCallback);
    }

    @Override
    protected void onStop() {
        super.onStop();
        holder.removeCallback(surfaceHolderCallback);
    }

    @Override
    protected void onDestroy() {
        sensorManager.unregisterListener(sensorEventListener2);
        super.onDestroy();
    }
}
