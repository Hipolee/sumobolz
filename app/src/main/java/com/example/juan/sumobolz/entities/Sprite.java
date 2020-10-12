package com.example.juan.sumobolz.entities;


import android.graphics.Bitmap;

import java.security.cert.CertificateNotYetValidException;

/**
 * The Sprite class represents an entity with an image, a position,
 * a velocity and other control fields.
 * <p>
 *
 * @author Juan
 */

public abstract class Sprite {

    /**
     * Defines the maximal velocity a sprite can have
     */
    private static float MAX_VELOCITY = 1000;

    /**
     * Vector defining the velocity for this sprite
     */
    private Vector2d velocity;

    /**
     * Vector defining the position of this sprite
     */
    private Vector2d position;

    /**
     * Is this sprite destroyed?
     */
    private boolean destroyed;

    /**
     * The bitmap for this sprite
     */
    private Bitmap bitmap;

    /**
     * The acceleration for this sprite
     */
    public Vector2d acceleration;

    /**
     * Constructs a sprite.
     *
     * @param bitmap - The bitmap for this sprite
     */
    public Sprite(Bitmap bitmap) {
        this(bitmap, 0, 0);
    }

    /**
     * Constructs a sprite, specifying its x and y positions when spawning
     *
     * @param bitmap - The bitmap for this sprite
     * @param x      - The x position
     * @param y      - The y position
     */
    public Sprite(Bitmap bitmap, float x, float y) {
        this.bitmap = bitmap;
        this.destroyed = false;
        position = new Vector2d(x, y);
        velocity = new Vector2d(0, 0);
        acceleration = new Vector2d(0, 0);
    }


    /**
     * Updates this sprite object's position according to its velocity with a given coefficient
     * <p>
     * Eventually, we could update animations in here.
     */
    public void update(float coefficient) {
        velocity = velocity.add(acceleration.multiply(coefficient));
        position = position.add(velocity.multiply(coefficient));
    }


    /**
     * Set the position of this sprite
     *
     * @param x - X position of the sprite.
     * @param y - Y position of the sprite.
     */
    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    /**
     * Sets the position of this sprite via a vector
     *
     * @param p - The vector
     */
    public void setPosition(Vector2d p) {
        position = p;
    }

    /**
     * Set the velocity for this sprite
     *
     * @param x - X velocity of the sprite.
     * @param y - Y velocity of the sprite.
     */
    public void setVelocity(float x, float y) {
        velocity.set(x, y);
    }

    /**
     * Add velocity for this sprite
     *
     * @param x - The X velocity to be added.
     * @param y - The Y velocity to be added.
     */
    public void addVelocity(float x, float y) {

        velocity.set(velocity.getX() + x, velocity.getY() + y);


        if (velocity.getX() > MAX_VELOCITY)
            velocity.setX(MAX_VELOCITY);
        else if (velocity.getX() < -MAX_VELOCITY)
            velocity.setX(-MAX_VELOCITY);
        else if (velocity.getY() > MAX_VELOCITY)
            velocity.setY(MAX_VELOCITY);
        else if (velocity.getY() < -MAX_VELOCITY)
            velocity.setY(-MAX_VELOCITY);

    }

    /**
     * Sets the bitmap for this sprite
     *
     * @param bitmap - The desired bitmap for this sprite
     */
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    /**
     * Gets the current bitmap of this sprite
     *
     * @return - The current bitmap
     */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /**
     * Gets the current position for this sprite
     *
     * @return - The position vector for this sprite
     */
    public Vector2d getPosition() {
        return position;
    }

    /**
     * Gets the current position vector for the center of this sprite
     *
     * @return - The position in the center of this sprite
     */
    public Vector2d getCenteredPosition() {
        return new Vector2d(position.getX() - (bitmap.getWidth() / 2), position.getY() - (bitmap.getWidth() / 2));
    }


    /**
     * Sets whether this sprite is destroyed
     *
     * @param destroyed - Is this sprite destroyed?
     */
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    /**
     * Gets if this sprite is destroyed
     *
     * @return - True if it is destroyed, false otherwise
     */
    public boolean isDestroyed() {
        return destroyed;
    }

    /**
     * Gets the width of this sprite
     *
     * @return - The width of this sprite
     */
    public float getWidth() {
        return bitmap.getWidth();
    }

    /**
     * Gets the height for this sprite
     *
     * @return - The desired height for this sprite.
     */
    public float getHeight() {
        return bitmap.getHeight();
    }


    /**
     * Returns this sprite as a string
     *
     * @return A string showing this sprites position and velocity
     */
    public String toString() {
        return " Position: [" + position + "]"
                + " Velocity: [" + velocity + "]";
    }

    /**
     * Gets the velocity vector for this sprite
     *
     * @return - The velocity vector
     */
    public Vector2d getVelocity() {
        return velocity;
    }

    /**
     * Sets the velocity vector for this sprite
     *
     * @param velocity - The velocity vector
     */
    public void setVelocity(Vector2d velocity) {
        this.velocity = velocity;
    }

    /**
     * Checks whether this sprite is intersecting with another sprite or not
     *
     * @param spriteB - The other sprite
     * @return - Whether this sprite is intersecting with another sprite or not
     */
    public abstract boolean intersects(Sprite spriteB);

    public Vector2d getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2d acceleration) {
        this.acceleration = acceleration;
    }
}