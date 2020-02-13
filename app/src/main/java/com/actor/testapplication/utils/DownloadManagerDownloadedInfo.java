package com.actor.testapplication.utils;

/**
 * Description: DownloadManager下载文件信息
 * Author     : 李大发
 * Date       : 2020/2/13 on 14:13
 *
 * @version 1.0
 */
public class DownloadManagerDownloadedInfo {
    public long id;//id
    public String localFileName;//文件下载路径
    public String mediaproviderUri;//媒体提供者Uri
    public String title;//标题
    public String description;//描述
    public String uri;//uri
    public int status;//状态
    public int mediaType;//
    public long totalSizeBytes;//总大小
    public long lastModifiedTime;//最后修改时间戳
    public long bytesDownloaded;//到目前为止下载的字节
    public String localUri;//localUri
    public String reason;//原因

    public DownloadManagerDownloadedInfo(long id, String localFileName, String mediaproviderUri, String title,
                                         String description, String uri, int status, int mediaType,
                                         long totalSizeBytes, long lastModifiedTime, long bytesDownloaded,
                                         String localUri, String reason) {
        this.id = id;
        this.localFileName = localFileName;
        this.mediaproviderUri = mediaproviderUri;
        this.title = title;
        this.description = description;
        this.uri = uri;
        this.status = status;
        this.mediaType = mediaType;
        this.totalSizeBytes = totalSizeBytes;
        this.lastModifiedTime = lastModifiedTime;
        this.bytesDownloaded = bytesDownloaded;
        this.localUri = localUri;
        this.reason = reason;
    }
}
