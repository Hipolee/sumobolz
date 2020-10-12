package com.example.juan.sumobolz.entities;


public class Vector2d {

    private float x, y;

    public Vector2d(float value) {
        this(value, value);
    }

    public Vector2d(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }

    public void copy(Vector2d vector2d) {
        this.x = vector2d.x;
        this.y = vector2d.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Vector2d add(Vector2d other) {
        float x = this.x + other.x;
        float y = this.y + other.y;
        return new Vector2d(x, y);
    }

    public Vector2d subtract(Vector2d other) {
        float x = this.x - other.x;
        float y = this.y - other.y;
        return new Vector2d(x, y);
    }

    public Vector2d multiply(float value) {
        return new Vector2d(value * x, value * y);
    }

    public float dotProduct(Vector2d other) {
        return other.x * x + other.y * y;
    }

    public float getLength() {
        return (float) Math.sqrt(dotProduct(this));
    }

    public Vector2d normalize() {
        float magnitude = getLength();
        if (magnitude == 0) {
            magnitude = 1;
        }
        x = x / magnitude;
        y = y / magnitude;
        return this;
    }

    @Override
    public String toString() {
        return "x: " + x + "y: " + y;
    }
}