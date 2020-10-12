package com.example.juan.sumobolz.helpers;

/**
 * The speed rate this game is going to run at
 */
public enum GameSpeed {
    VERY_SLOW(0.0006f),
    SLOW(0.003f),
    NORMAL(0.006f),
    FAST(0.015f),
    VERY_FAST(0.02f);

    private float ooefficient;

    GameSpeed(float coefficient) {
        this.ooefficient = coefficient;
    }

    public float getCoefficient() {
        return this.ooefficient;
    }

}
