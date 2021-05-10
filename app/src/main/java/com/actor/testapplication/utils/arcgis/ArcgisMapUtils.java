package com.actor.testapplication.utils.arcgis;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.view.MapView;

/**
 * description: arcgis 地图工具类:
 * 1.地址
 *  https://github.com/Esri/arcgis-runtime-samples-android
 *  https://developers.arcgis.com/android/latest/
 *
 * 2.添加依赖
 *  在 project 的build.gradle中添加:
 *  allprojects {
 *      repositories {
 *          maven { url 'https://esri.jfrog.io/artifactory/arcgis' }
 *      }
 *  }
 *
 *  在 module 的build.gradle中添加:
 *  //https://github.com/Esri/arcgis-runtime-samples-android
 *  implementation 'com.esri.arcgisruntime:arcgis-android:100.4.0'
 *
 * 3.在清单文件 AndroidManifest.xml 中, <application 同级, 添加:
 *     <!--OpenGL ES支持, arcgis-runtime-samples-android -->
 *     <uses-feature
 *         android:glEsVersion="0x00020000"
 *         android:required="true" />
 *
 * 4.在布局文件中:
 *  <com.esri.arcgisruntime.mapping.view.MapView
 *      android:id="@+id/map_view"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent" />
 *
 * date       : 2021/4/6 on 13
 * @version 1.0
 */
public class ArcgisMapUtils {//from land产权

    /**
     * 获取目前缩放
     */
    public static double getMapScale(MapView mapView) {
        return mapView.getMapScale();
    }

    /**
     * 获取最大max缩放
     */
    public static double getMaxScale(MapView mapView) {
        ArcGISMap arcGISMap = mapView.getMap();
        return arcGISMap.getMaxScale();
    }

    /**
     * 获取最小min缩放
     */
    public static double getMinScale(MapView mapView) {
        ArcGISMap arcGISMap = mapView.getMap();
        return arcGISMap.getMinScale();
    }

    /**
     * 设置缩放
     */
    public static void setViewpointScaleAsync(MapView mapView, double scale) {
        mapView.setViewpointScaleAsync(scale);
    }
}
