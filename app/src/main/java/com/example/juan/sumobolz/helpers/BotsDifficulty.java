package com.example.juan.sumobolz.helpers;

/**
 * Created by Juan on 20-Mar-18
 */

/**
 * An enumeration defining the difficulty for bots artificial intelligence
 */
public enum BotsDifficulty {
    VERY_EASY(5),
    EASY(12),
    NORMAL(20),
    HARD(25),
    VERY_HARD(30);

    private int coefficient;

    BotsDifficulty(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }
}
