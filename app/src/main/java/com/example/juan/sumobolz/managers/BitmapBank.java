package com.example.juan.sumobolz.managers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import com.example.juan.sumobolz.R;

/**
 * Created by Juan on 11-Feb-18
 */

/*
 * Responsible for loading bitmap instances from resources and keeping references to them.
 */
public class BitmapBank {

    /**
     * The bitmap for a blue ball
     */
    private Bitmap mBlueBall;

    /**
     * The bitmap for a green ball
     */
    private Bitmap mGreenBall;

    /**
     * The bitmap for a koala ball
     */
    private Bitmap mKoalaBall;

    /**
     * The bitmap for a panda ball
     */
    private Bitmap mPandaBall;

    /**
     * Loads bitmaps from the resources
     *
     * @param res resources reference
     */
    public BitmapBank(Resources res) {
        mBlueBall = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.blue_circle), 300, 300);
        mGreenBall = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.green_circle), 300, 300);
        mKoalaBall = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.koala_circle), 300, 300);
        mPandaBall = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.panda_circle), 300, 300);
    }

    /**
     * @return - The blue ball bitmap
     */
    public Bitmap getBlueBall() {
        return mBlueBall;
    }

    /**
     * @return - The green ball bitmap
     */
    public Bitmap getGreenBall() {
        return mGreenBall;
    }

    public Bitmap getKoalaBall() {
        return mKoalaBall;
    }

    public void setmKoalaBall(Bitmap mKoalaBall) {
        this.mKoalaBall = mKoalaBall;
    }

    /**
     * Rotates given bitmap according to passed angle, using a matrix object
     *
     * @param source - Bitmap that needed to be rotated
     * @param angle  - Rotation angle
     * @return - The rotated bitmap
     */
    public static Bitmap RotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);

        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public Bitmap getPandaBall() {
        return mPandaBall;
    }

    public void setmPandaBall(Bitmap mPandaBall) {
        this.mPandaBall = mPandaBall;
    }

    /**
     * Resize a bitmap to the desired width and height
     *
     * @param bm        - The bitmap
     * @param newWidth  - The new width
     * @param newHeight - The new height
     * @return - The resized bitmap
     */
    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {

        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Create a matrix with the new values
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);


        // Crete the resized bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        // Recycle the old bitmap
        bm.recycle();
        return resizedBitmap;
    }
}
