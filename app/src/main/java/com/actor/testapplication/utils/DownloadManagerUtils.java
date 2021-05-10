package com.actor.testapplication.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.LongSparseArray;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.actor.myandroidframework.utils.ConfigUtils;
import com.actor.myandroidframework.utils.LogUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Description: 下载管理器
 * Date       : 2020/1/31 on 17:26
 *
 * @version 1.0
 */
public class DownloadManagerUtils {

    private static       DownloadManager                     downloadManager;
    private static       DownloadCompleteReceiver            downloadCompleteReceiver;
    private static final LongSparseArray<OnDownloadListener> listeners = new LongSparseArray<>();

    public static DownloadManager getDownloadManager() {
        if (downloadManager == null) {//获取系统服务
            downloadManager = (DownloadManager) ConfigUtils.APPLICATION.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        return downloadManager;
    }

    /**
     * 发起一个下载请求
     * @param url 下载地址
     * @param fileName 下载文件名称, 例: app.apk
     * @param title 在标题栏显示的标题, 例: 今日头条
     * @param description 描述, 例: 今日头条正在下载/更新中.....
     * @param listener 下载监听
     * @return 返回下载id
     */
    public static long downLoad(String url, String fileName, String title, String description,
                                @Nullable OnDownloadListener listener) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //设置允许MediaScanner扫描
        request.allowScanningByMediaScanner();
        //设置什么网络情况下可以下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
        //设置漫游状态下是否可以下载
        request.setAllowedOverRoaming(true);
        //设置通知栏的message
        request.setDescription(description);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(ConfigUtils.APPLICATION, Environment.DIRECTORY_DOWNLOADS, fileName);
        //设置类型为.apk
//        request.setMimeType("application/vnd.android.package-archive");
        //通知栏显示下载进度, 默认true
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //设置通知栏的标题
        request.setTitle(title);
        request.setVisibleInDownloadsUi(true);
        //进行下载
        long id = getDownloadManager().enqueue(request);
        if (listener != null) listeners.put(id, listener);
        //注册下载广播
        if (downloadCompleteReceiver == null) {
            downloadCompleteReceiver = new DownloadCompleteReceiver();
            IntentFilter intentFilter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
            ConfigUtils.APPLICATION.registerReceiver(downloadCompleteReceiver, intentFilter);
        }
        return id;
    }

    /**
     * 查询下载信息
     * @param downloadStatus 下载状态, 可传null:
     *               @see DownloadManager#STATUS_PENDING    等待
     *               @see DownloadManager#STATUS_RUNNING    下载中
     *               @see DownloadManager#STATUS_PAUSED     停止
     *               @see DownloadManager#STATUS_SUCCESSFUL 成功
     *               @see DownloadManager#STATUS_FAILED     失败
     * @param ids 根据ids查询, 可传null
     */
    public static @NonNull List<DownloadManagerDownloadedInfo> query(@Nullable Integer downloadStatus, long... ids) {
        DownloadManager.Query query = new DownloadManager.Query();
        if (downloadStatus != null) query.setFilterByStatus(downloadStatus);
        query.setFilterById(ids);
        Cursor cursor = getDownloadManager().query(query);
        List<DownloadManagerDownloadedInfo> list = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                try {
                    //id
                    long id = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_ID));
                    //文件下载路径
                    String localFileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                    //媒体提供者Uri
                    String mediaproviderUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIAPROVIDER_URI));
                    //标题
                    String title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE));
                    //描述
                    String description = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_DESCRIPTION));
                    //uri
                    String uri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                    //状态
                    int status = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS));
                    //
                    int mediaType = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE));
                    //总大小
                    long totalSizeBytes = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                    //最后修改时间戳
                    long lastModifiedTime = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP));
                    //到目前为止下载的字节
                    long bytesDownloaded = cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                    //localUri
                    String localUri = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    //原因
                    String reason = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_REASON));
                    list.add(new DownloadManagerDownloadedInfo(id, localFileName, mediaproviderUri,
                            title, description, uri, status, mediaType, totalSizeBytes,
                            lastModifiedTime, bytesDownloaded, localUri, reason));
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    cursor.close();
                }
            }
        }
        return list;
    }

    /**
     * 取消下载, 并从DownloadManager中删除(包括部分&已完成).
     * @param ids 开始下载的id
     */
    public static void remove(long... ids) {
        getDownloadManager().remove(ids);
        for (long id : ids) {
            listeners.remove(id);
        }
    }

    public interface OnDownloadListener {
        void onDownloadCompleted(long id);
        void onNotificationClicked(long id, long[] notificationClickDownloadIds);
    }

    /**
     * Description: 下载文件的广播接收者
     * 需要在 "清单文件中注册" or "代码注册"
     * <receiver android:name=".receiver.DownloadCompleteReceiver">
     *     <intent-filter>
     *         <action android:name="android.intent.action.DOWNLOAD_COMPLETE"/>
     *     </intent-filter>
     * </receiver>
     */
    public static class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
                //在广播中取出下载任务的id
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                LogUtils.formatError("编号：%d的下载任务已经完成！", true, id);
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.get(i).onDownloadCompleted(id);
                }
            } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                //?
                long[] ids = intent.getLongArrayExtra(DownloadManager.EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS);
                LogUtils.formatError("编号：%d的下载任务被点击了, notificationClickDownloadIds=%s！",
                        true, id, Arrays.toString(ids));
                for (int i = 0; i < listeners.size(); i++) {
                    listeners.get(i).onNotificationClicked(id, ids);
                }
            } else LogUtils.error("intent.getAction():" + intent.getAction(), true);
        }
    }

    //其它方法
    private void otherMethod() {
        long id = downloadManager.addCompletedDownload("title", "desc", true, "mimeType",
                "path", 1L, true);
        String mimeType = downloadManager.getMimeTypeForDownloadedFile(id);//id
        Uri uri = downloadManager.getUriForDownloadedFile(id);//id
        try {
            ParcelFileDescriptor fileDescriptor = downloadManager.openDownloadedFile(id);//id
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
