package com.actor.testapplication.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

/**
 * Description: 资产目录工具,可以在Application的时候就开始拷贝.把文件传到/data/data/包名/files/
 * Date       : 2018/1/8 on 18:08
 * @deprecated 下一版本更新
 */
@Deprecated
public class AssetsUtils {

    /**
     * 读取资源文件,返回流
     * @param fileName 资源文件中文件名称
     */
    public static InputStream openFile(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }
}
