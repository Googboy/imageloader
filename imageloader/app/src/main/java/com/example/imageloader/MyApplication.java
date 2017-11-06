package com.example.imageloader;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;

/**
 * Created by 潘硕 on 2017/11/6.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480,800).discCacheExtraOptions(480,800,null,1,null)
                .threadPoolSize(3).threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                .memoryCacheSize(2*1024*1024)
                .discCacheSize(50*1024*1024)
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(new File(Environment.getExternalStorageDirectory()+"myApp/imageCache")))
                .defaultDisplayImageOptions(getDisplayOptions())
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                .writeDebugLogs().build();
         ImageLoader.getInstance().init(config);

    }

    private DisplayImageOptions getDisplayOptions() {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                   .showImageForEmptyUri(R.mipmap.ic_launcher)
                   .showImageOnFail(R.mipmap.ic_launcher)
                   .cacheInMemory(true)
                   .cacheOnDisc(true)
                   .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                   .bitmapConfig(Bitmap.Config.RGB_565)
                   .resetViewBeforeLoading(true)
                   .displayer(new RoundedBitmapDisplayer(20))
                   .displayer(new FadeInBitmapDisplayer(100))
                     .build();
        return options;
    }
//    private void initImageLoader(){
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).imageDownloader(
//                new BaseImageDownloader(this,60*1000,60*1000)
//        ).build();
//        ImageLoader.getInstance().init(config);
//    }
}
