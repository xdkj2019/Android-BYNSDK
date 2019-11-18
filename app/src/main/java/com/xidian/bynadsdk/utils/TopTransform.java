package com.xidian.bynadsdk.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

/**
 * Created by Administrator on 2019/11/11.
 */

public class TopTransform implements Transformation<Bitmap> {
    private BitmapPool mBitmapPool;
    private Context context;
    private float heightPercentage;
    public TopTransform(Context context,float heightPercentage) {
        this.context = context;
        this.heightPercentage = heightPercentage;
        mBitmapPool = Glide.get(context).getBitmapPool();
    }
    private int mWidth;
    private int mHeight;

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        mWidth = source.getWidth();
        mHeight = mWidth * Utils.getScreenHeight(context) / Utils.getScreenWidth(context);//图片满屏时的高度

       // mHeight = (int) (heightPercentage*mHeight);


        if(mHeight>source.getHeight()){
            mHeight=source.getHeight();
        }
        Bitmap.Config config =
                source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
        Bitmap bitmap = mBitmapPool.get(mWidth, mHeight, config);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(mWidth, mHeight, config);
        }

        float scaleX = (float) mWidth / source.getWidth();
        float scaleY = (float) mHeight / source.getHeight();
        float scale = Math.max(scaleX, scaleY);

        float scaledWidth = scale * source.getWidth();
        float scaledHeight = scale * source.getHeight();
        float left = (mWidth - scaledWidth) / 2;
        float top = 0;
        RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(source, null, targetRect, null);

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return this.getClass().getName();
    }

}
