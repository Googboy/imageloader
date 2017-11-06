package com.example.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

/**
 * Created by 潘硕 on 2017/11/6.
 */

public class ImageLoaderUtil {
    private static final int THREAD_COUNT = 2;
    private static final int PRIORITY = 2;
    private static final int DISK_CACHE_SIZE = 50*1024*1024;
    private static final int CONNECTION_TIME_OUT = 5*1000;
    private static final int READ_TIME_OUT = 30*1000;
    private static ImageLoaderUtil util = null;
    private static ImageLoader Loader = null;

    private ImageLoaderUtil(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(THREAD_COUNT)
                .threadPriority(Thread.NORM_PRIORITY - PRIORITY)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .diskCacheSize(DISK_CACHE_SIZE)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候进行MD5加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(getDefaultOptions())
                .imageDownloader(new BaseImageDownloader(context,CONNECTION_TIME_OUT,READ_TIME_OUT))
                .build();
        ImageLoader.getInstance().init(config);
        Loader = ImageLoader.getInstance();

    }

    private DisplayImageOptions getDefaultOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
                .considerExifParams(true)//是否考虑JPEG图像EXIF参数（旋转，翻转）
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .decodingOptions(new BitmapFactory.Options())//设置图片的解码配置
                .resetViewBeforeLoading(true)//设置图片下载前是否重置复位
                .displayer(new FadeInBitmapDisplayer(300))//设置加载图片的task（这里是渐现）
                .build();
        return options;
    }


    public static ImageLoaderUtil getInstance(Context context){
        if (util == null){
            synchronized (ImageLoaderUtil.class){
                if (util == null){
                    util = new ImageLoaderUtil(context);
                }
            }
        }
        return util;
    }
}
