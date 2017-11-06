package com.example.imageload;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBar;
    private String urlString = "http://image.tianjimedia.com/uploadImages/2012/067/N80N0GUA36N0.jpg";
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.img_load);
        progressBar = (ProgressBar) findViewById(R.id.pb_loading);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置URL为空时显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)//加载失败时显示的图片
                .resetViewBeforeLoading(true)//设置下载的图片下载钱是否重置,复位
                .cacheInMemory(true)//设置下载图片是否缓存到内存中
                .imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(300))
                .build();
        imageLoader.displayImage(urlString,imageView,options,new MySimpleLoading(progressBar));
    }

    private class MySimpleLoading extends SimpleImageLoadingListener {
        private ProgressBar progressBar;
        public MySimpleLoading(ProgressBar progressBar) {
            super();
            this.progressBar = progressBar;
        }

        @Override
        public void onLoadingStarted(String s, View view) {
            super.onLoadingStarted(s,view);
            progressBar.setVisibility(view.VISIBLE);
        }

        @Override
        public void onLoadingFailed(String s, View view, FailReason failReason) {
            String message = null;
            switch (failReason.getType()){
                case IO_ERROR:
                    message = "Input/Output error";
                    break;
                case DECODING_ERROR:
                    message = "Image can't be decoded";
                    break;
                case NETWORK_DENIED:
                    message = "Downloads are denied";
                    break;
                case OUT_OF_MEMORY:
                    message = "Out of Memory error";
                    break;
                case UNKNOWN:
                    message = "Unknown error";
                    break;
            }
            System.out.println(message);
            progressBar.setVisibility(View.GONE);
            //System.out.println(s);
        }
//        public void clearMemoryCache(){
//            imageLoader.clearMemoryCache();
//        }

        @Override
        public void onLoadingComplete(String s, View view, Bitmap bitmap) {
            super.onLoadingComplete(s,view,bitmap);
            progressBar.setVisibility(View.GONE);
            System.out.println(s);
        }

        @Override
        public void onLoadingCancelled(String s, View view) {

        }
    }
}
