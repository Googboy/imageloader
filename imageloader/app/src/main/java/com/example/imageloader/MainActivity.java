package com.example.imageloader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MainActivity extends AppCompatActivity {
    private ImageLoader loader;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loader = ImageLoader.getInstance();
        imageView = (ImageView) findViewById(R.id.image);
        loader.displayImage("http://image.tianjimedia.com/uploadImages/2012/067/N80N0GUA36N0.jpg",imageView);

    }
}
