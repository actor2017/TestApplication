package com.actor.cpptest;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

/**
 * description: 锅炉压力
 *
 * @author : ldf
 * date       : 2021/6/9 on 16
 * @version 1.0
 */
public class GuoLuPress {

    private static final String TAG = "GuoLuPress";

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    static {
        System.loadLibrary("native-lib");
    }

    protected static OnPressListener listener;
    protected static boolean isStartMonitor = false;

    /**
     * 开始监测锅炉压力
     */
    public static void startMonitor(OnPressListener listener) {
        GuoLuPress.listener = listener;
        if (!isStartMonitor) {
            isStartMonitor = true;
            //子线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    _getPress();
                }
            }).start();
        } else {
            Log.e(TAG, "startMonitor: 已经在监测了, 请勿重复调用.");
        }
    }

    /**
     * C语音获取到压力后, 回调这个方法
     */
    protected void onGetProgress(final int press) {
        if (listener != null) {
            HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    listener.onPress(press);
                }
            });
        }
    }

    /**
     * 停止监测
     */
    public static void stopMonitor() {
        isStartMonitor = false;
        setMonitorState(false);
    }



    /**
     * 设置监测状态
     */
    protected static native void setMonitorState(boolean isStartMonitor);

    /**
     * 获取锅炉压力值, 会回调方法: {@link #onGetProgress(int)}
     */
    protected static native void _getPress();

    public interface OnPressListener {
        /**
         * 返回压力值, 子线程
         * @param progress 0-100
         */
        void onPress(int progress);
    }
}
