package com.example.juan.sumobolz.entities;

import android.graphics.Bitmap;
import android.graphics.Matrix;


/**
 * This class represents a simple ball, it has a mas
 */
public class Ball extends Sprite {

    /**
     * The circle that defines this ball's boundaries
     */
    private Circle mCircle;

    /**
     * This ball's mass
     */
    private float mMass;

    /**
     * Constructs a ball
     *
     * @param bitmap - The bitmap that this ball will use
     */
    public Ball(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    /**
     * Constructs a ball, specifying the position when it spawns
     *
     * @param bitmap - The bitmap that this ball will use
     * @param x      - The x position of this ball
     * @param y      - The y position of this ball
     */
    public Ball(Bitmap bitmap, float x, float y) {
        super(bitmap, x, y);
        resize(150, 150);
        mCircle = new Circle(getBitmap().getWidth() / 2, getPosition());
        mMass = mCircle.getRadius() * 3;
    }


    @Override
    public boolean intersects(Sprite spriteB) {

        return spriteB instanceof Ball && checkCircleCollide(getCenteredPosition(), mCircle.getRadius(), spriteB.getCenteredPosition(), ((Ball) spriteB).getCircle().getRadius());
    }

    /**
     * Checks if this ball is colliding with another ball according to their stats
     *
     * @param position1 - The position of this ball
     * @param r1        - The radius of this ball
     * @param position2 - The position of the other ball
     * @param r2        - The radius of the other ball
     * @return - Whether this ball is colliding with the other ball or not
     */
    private boolean checkCircleCollide(Vector2d position1, float r1, Vector2d position2, float r2) {
        return Math.abs((position1.getX() - position2.getX()) * (position1.getX() - position2.getX()) + (position1.getY() - position2.getY()) * (position1.getY() - position2.getY())) < (r1 + r2) * (r1 + r2);
    }

    /**
     * Resizes this ball to the desire new height and width
     *
     * @param newWidth  - The new width
     * @param newHeight - The new height
     */
    private void resize(int newWidth, int newHeight) {
        int width = getBitmap().getWidth();
        int height = getBitmap().getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // Create a matrix with the new values
        Matrix matrix = new Matrix();

        // Resize the bitmap
        matrix.postScale(scaleWidth, scaleHeight);

        // Crete the resized bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(getBitmap(), 0, 0, width, height, matrix, false);

        setBitmap(resizedBitmap);
    }

    /**
     * Gets the mass of this ball
     *
     * @return - The mass of the ball
     */
    public float getMass() {
        return mMass;
    }

    /**
     * Gets the radius of this ball
     *
     * @return - The radious of the ball
     */
    public float getRadius() {
        return mCircle.getRadius();
    }

    /**
     * Sets the mass of the ball
     *
     * @param mMass - The mass of the ball to set
     */
    public void setMass(float mMass) {
        this.mMass = mMass;
    }

    public Circle getCircle() {
        return mCircle;
    }

}
