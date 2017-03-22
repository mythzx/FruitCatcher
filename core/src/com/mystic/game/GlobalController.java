package com.mystic.game;


/** Created by Gavin
 * This class is intended to pass data over to the Main Android application
 */

public abstract class GlobalController {
    //Contains the score of each game
    public int score = 0;
    //Setter Method for score
    public abstract void setScore(int s);
    //Abstract class to allow Selecting the Activity to move to at main Application
    public abstract void move();
}
