package com.example.administrator.getpixels;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private Bitmap bitmap;
    private ImageView imageView;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_img);
    }

    //红 黄 蓝 绿
    public Bitmap createBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < width / 2; i++) {
            for (int j = 0; j < height / 2; j++) {//左上块
                bitmap.setPixel(i, j, Color.RED);//红色
            }
            for (int j = height / 2; j < height; j++) {//左下块
                bitmap.setPixel(i, j, Color.BLUE);//蓝色
            }
        }

        for (int i = width / 2; i < width; i++) {
            for (int j = 0; j < height / 2; j++) {//右上块
                bitmap.setPixel(i, j, Color.YELLOW);//黄色
            }
            for (int j = height / 2; j < height; j++) { //右下块
                bitmap.setPixel(i, j, Color.GREEN);//绿色
            }
        }
        return bitmap;
    }

    //一行红的，一行黑的
    //但是程序显示的结果是不是这样的
    //可以把bitmap保存下来再看
    public Bitmap createBitmap2() {
        Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
//        for (int i = 0; i < width; i++) {
//            for (int j = 0; j < height; j++) {
//                if (j % 2 == 0)
//                    bitmap.setPixel(i, j, Color.RED);
//                else
//                    bitmap.setPixel(i, j, Color.BLACK);
//            }
//        }
        for (int i = 0; i < width * height; i++) {
            int x = i / width;
            int y = i % width;
            if (y % 2 == 0)
                bitmap.setPixel(x, y, Color.RED);
            else
                bitmap.setPixel(x, y, Color.BLACK);
        }
        return bitmap;
    }

    public void createBitmap(View view) {
        bitmap = createBitmap();
        imageView.setImageBitmap(bitmap);
    }

    public void createBitmap2(View view) {
        bitmap = createBitmap2();
        imageView.setImageBitmap(bitmap);
    }


    public void saveBitmap(View view) {
        index++;
        String strPath = Environment.getExternalStorageDirectory().toString() + "/" + "bitmap" + index + ".jpg";
        saveBitmap(bitmap, strPath);
    }

    public void getPixels1(View view) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //新建像素点数组
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

        //把所有像素点设置为黑色
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = Color.WHITE;
        }
        //imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //显示左上角图案
//        bitmap.getPixels(pixels, 0, width, 0, 0, width / 2, height / 2);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //右上的
//        bitmap.getPixels(pixels, 0, width, width / 2, 0, width / 2, height / 2);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //左下的 蓝色
//        bitmap.getPixels(pixels, 0, width, 0, height / 2, width / 2, height / 2);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //右下的 绿色
//        bitmap.getPixels(pixels, 0, width, width / 2, height / 2, width / 2, height / 2);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //在正中间显示绿色
//        //offset = x*width+y
//        bitmap.getPixels(pixels, width * 100+100, width, 200, 200, width / 2, height / 2);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

        bitmap.getPixels(pixels, 0, width / 2, width / 2, height / 2, width / 2, height / 2);
        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth(), bitmap.getHeight()));

//        //数组放大4倍
//        pixels = new int[width * height * 4];
//        //只改变了offset参数
//        bitmap.getPixels(pixels, 0, width * 2, 0, 0, width, height);
//        bitmap.getPixels(pixels, width, width * 2, 0, 0, width, height);
//        bitmap.getPixels(pixels, width * 2 * height, width * 2, 0, 0, width, height);
//        bitmap.getPixels(pixels, width * 2 * height + width, width * 2, 0, 0, width, height);
//        imageView.setImageBitmap(setPixels(pixels, bitmap.getWidth() * 2, bitmap.getHeight() * 2));

    }

    //pixels --> bitmap
    public Bitmap setPixels(int[] pixels, int width, int height) {
        //return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888);
        return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
    }

    public static void saveBitmap(Bitmap bitmap, String path) {
        try {
            OutputStream os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
