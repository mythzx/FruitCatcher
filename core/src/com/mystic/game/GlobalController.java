package com.mystic.game;


/**
 * Created by Gavin
 */

public interface GlobalController {
    public int score = 0;
    abstract void setScore(int s);
    abstract void move();
}
