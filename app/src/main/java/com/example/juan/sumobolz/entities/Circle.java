package com.example.juan.sumobolz.entities;

/**
 * Created by Juan on 14-Feb-18
 */

public class Circle {
    private float mRadius;
    private Vector2d position;

    public Circle(float mRadius, Vector2d position) {
        this.mRadius = mRadius;
        this.position = position;
    }

    public float getRadius() {
        return mRadius;
    }

    public void setRadius(float mRadius) {
        this.mRadius = mRadius;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }
}
