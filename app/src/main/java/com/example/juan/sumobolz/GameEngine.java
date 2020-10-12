package com.example.juan.sumobolz;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import com.example.juan.sumobolz.entities.Ball;
import com.example.juan.sumobolz.entities.EnemyBall;
import com.example.juan.sumobolz.entities.Sprite;
import com.example.juan.sumobolz.entities.Vector2d;
import com.example.juan.sumobolz.helpers.BotsDifficulty;
import com.example.juan.sumobolz.helpers.GameSpeed;
import com.example.juan.sumobolz.managers.AppConstants;
import com.example.juan.sumobolz.managers.SpriteManager;

import java.util.Random;

/**
 * Created by Juan on 11-Feb-18
 */

/*
 * Stores all object references that relevant for the game display
 * Calls objects business logic methods, and draw them to the given Canvas from DisplayThread
 * */
public class GameEngine {

    private static final Random rand = new Random();
    /**
     * Object to synchronize some of the methods in order to avoid reentering critical sections
     */
    private static final Object mSync = new Object();

    /**
     * The sprites in this game
     */
    private SpriteManager mSpriteMan;

    /**
     * The coefficients of the last touched events
     */
    private float mLastTouchedX, mLastTouchedY;

    /**
     * The game speed
     */
    private float mGameSpeed;

    /**
     * The bots difficulty for the solo mode
     */
    private BotsDifficulty difficulty;

    /**
     * Constructs a GameEngine with a normal bot difficulty
     *
     * @param spriteManager - The sprite manager for this GameEngine instance
     */
    public GameEngine(SpriteManager spriteManager) {
        this(spriteManager, BotsDifficulty.VERY_HARD, GameSpeed.NORMAL);
    }


    public GameEngine(SpriteManager spriteManager, BotsDifficulty diff) {
        this(spriteManager, diff, GameSpeed.NORMAL);
    }

    /**
     * Constructs a GameEngine
     *
     * @param spriteManager - The sprite manager for this GameEngine instance
     * @param difficult     - The bots difficulty for this GameEngine instance
     */
    public GameEngine(SpriteManager spriteManager, BotsDifficulty difficult, GameSpeed speed) {
        mSpriteMan = spriteManager;
        mGameSpeed = speed.getCoefficient();
        difficulty = difficult;
    }


    /**
     * Updates all relevant object's logic
     */
    void Update() {
        cleanupSprites();
        updateSprites();
        checkCollisions();
    }

    private void handleUpdate(Sprite sprite) {
        sprite.update(mGameSpeed);
        sprite.addVelocity((-sprite.getVelocity().getX()) * 0.001f, (-sprite.getVelocity().getY()) * 0.001f);
        // Vector2d v = sprite.getVelocity().add(AppConstants.getmMainPlayer().getVelocity());
        // sprite.addVelocity(v.getX(), v.getY());

        if (sprite instanceof EnemyBall) {

            if (!sprite.equals(AppConstants.getmMainPlayer())) {

                float xSpeed = (AppConstants.getmMainPlayer().getPosition().getX() - sprite.getPosition().getX()) / 1f;
                float ySpeed = (AppConstants.getmMainPlayer().getPosition().getY() - sprite.getPosition().getY()) / 1f;

                float factor = difficulty.getCoefficient() / (float) Math.sqrt(xSpeed * xSpeed + ySpeed * ySpeed);

                xSpeed *= factor;
                ySpeed *= factor;

                sprite.addVelocity(xSpeed, ySpeed);

            }
        }

        Sprite s = mSpriteMan.getAllSprites().get(0);
        s.addVelocity(mLastTouchedX, mLastTouchedY);
        // sprite.addVelocity((mLastTouchedX + (float)Math.random()) % 50, (mLastTouchedY + (float)Math.random()) % 50);
    }

    private void updateSprites() {
        synchronized (mSync) {
            for (Sprite sprite : mSpriteMan.getAllSprites()) {
                // Update the sprite's position according to time
                handleUpdate(sprite);
            }
            mLastTouchedX = 0;
            mLastTouchedY = 0;
        }
    }


    private void cleanupSprites() {
        mSpriteMan.cleanupSprites();
    }

    /**
     * Checks collisions for all objects
     */
    private void checkCollisions() {

        // We make sure to not check collections for any removed objects
        mSpriteMan.resetCollisionsToCheck();

        synchronized (mSync) {
            // check each sprite against other sprite objects.
            for (Sprite spriteA : mSpriteMan.getCollisionsToCheck()) {

                for (Sprite spriteB : mSpriteMan.getAllSprites()) {

                    if (handleCollision(spriteA, spriteB)) {

                        // Objects have collided and the collision has been handled.
                        // The break helps optimize the collisions

                        //  The break statement means one object only hits another
                        // object as opposed to one object hitting many objects.
                        // If we want more precision we can comment-out the break statement.
                        break;
                    }
                }
            }
        }

    }

    private boolean handleCollision(Sprite spriteA, Sprite spriteB) {

        if (spriteA != spriteB) {
            if (spriteA.intersects(spriteB)) {

                if (spriteA instanceof Ball && spriteB instanceof Ball) {


                    // MORE COMPLEX COLLISION
                    Ball ballOne = (Ball) spriteA;
                    Ball ballTwo = (Ball) spriteB;

                    Vector2d delta = ballOne.getPosition().subtract(ballTwo.getPosition());

                    float r = ballOne.getRadius() + ballTwo.getRadius();

                    float dist2 = delta.dotProduct(delta);

                    if (dist2 > r * r) return false; // they aren't colliding


                    float d = delta.getLength();

                    Vector2d mtd;
                    if (d != 0.0f) {
                        mtd = delta.multiply(((ballOne.getRadius() + ballTwo.getRadius()) - d) / d); // minimum translation distance to push balls apart after intersecting
                    } else // Special case. Balls are exactly on top of each other.  Don't want to divide by zero.
                    {
                        d = ballTwo.getRadius() + ballOne.getRadius() - 1.0f;
                        delta = new Vector2d(ballTwo.getRadius() + ballOne.getRadius(), 0.0f);

                        mtd = delta.multiply(((ballOne.getRadius() + ballTwo.getRadius()) - d) / d);
                    }

                    // resolve intersection
                    float im1 = 1 / ballOne.getMass(); // inverse mass quantities
                    float im2 = 1 / ballTwo.getMass();

                    // push-pull them apart
                    ballOne.setPosition(ballOne.getPosition().add(mtd.multiply(im1 / (im1 + im2))));
                    ballTwo.setPosition(ballTwo.getPosition().subtract(mtd.multiply(im2 / (im1 + im2))));

                    // Impact speed
                    Vector2d v = (ballOne.getVelocity().subtract(ballTwo.getVelocity()));
                    float vn = v.dotProduct(mtd.normalize());

                    // Sphere intersecting but moving away from each other already
                    if (vn > 0.0f) return false;

                    // Collision impulse
                    float i = (-(1.0f + 3.0f) * vn) / (im1 + im2);
                    Vector2d impulse = mtd.multiply(i);

                    // Change in momentum
                    ballOne.setVelocity(ballOne.getVelocity().add(impulse.multiply(im1)));
                    ballTwo.setVelocity(ballTwo.getVelocity().subtract(impulse.multiply(im2)));

                }


                Log.i("INFO", "Balls are colliding my brother!");

                return true;
            }
        }
        return false;
    }

    /**
     * Draws all objects according to their parameters
     *
     * @param canvas canvas on which will be drawn the objects
     */
    void Draw(Canvas canvas) {
        controlBoundaries(canvas);
        drawSprites(canvas);
    }

    /**
     * Controls the boundaries for every sprite in the canvas
     *
     * @param c - The canvas
     */
    private void controlBoundaries(Canvas c) {
        synchronized (mSync) {
            for (Sprite b : mSpriteMan.getAllSprites()) {
                if (b.getPosition().getY() + (b.getHeight()) > c.getHeight()) {
                    b.setPosition(b.getPosition().getX(), c.getHeight() - (b.getHeight()));
                    b.getVelocity().setY(-b.getVelocity().getY());
                }

                if (b.getPosition().getX() + b.getWidth() > c.getWidth()) {
                    b.setPosition(c.getWidth() - b.getWidth(), b.getPosition().getY());
                    b.getVelocity().setX(-b.getVelocity().getX());
                }

                if (b.getPosition().getX() < 0) {
                    b.setPosition(0, b.getPosition().getY());
                    b.getVelocity().setX(-b.getVelocity().getX());
                }

                if (b.getPosition().getY() < 0) {
                    b.setPosition(b.getPosition().getX(), 0);
                    b.getVelocity().setY(-b.getVelocity().getY());
                }
            }
        }
    }


    private void drawSprites(Canvas canvas) {
        synchronized (mSync) {
            for (Sprite s : mSpriteMan.getAllSprites()) {
                canvas.drawBitmap(s.getBitmap(), s.getPosition().getX(), s.getPosition().getY(), new Paint());
            }
        }
    }

    /**
     * Crates a new bubble at the given coordinates
     *
     * @param touchX x coordinates of the touch event
     * @param touchY y coordinates of the touch event
     */
    void createNewBall(int touchX, int touchY) {
        synchronized (mSync) {
            mSpriteMan.addSprites(new EnemyBall(AppConstants.GetBitmapsBank().getPandaBall(), touchX, touchY));
        }
    }

    /**
     * Sets previous touch coordinates
     *
     * @param x - Current touch x coordinate
     * @param y - Current touch y coordinate
     */
    void SetLastTouch(float x, float y) {
        mLastTouchedX = x;
        mLastTouchedY = y;
    }

    /**
     * Adds a ball to the game
     *
     * @param b - The ball to be added
     * @see Ball
     */
    public void addBall(Ball b) {
        mSpriteMan.addSprites(b);
    }

}