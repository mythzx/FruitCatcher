package com.mystic.game;

import com.badlogic.gdx.Gdx;

/**
 * Created by Gavin
 */

public class gameSetting {
    //Android Screen Width and Height
    //public static final int VIEWPORT_WIDTH = Gdx.app.getGraphics().getWidth();
    //public static final int VIEWPORT_HEIGHT = Gdx.app.getGraphics().getHeight();

    public static final int VIEWPORT_WIDTH = 720;
    public static final int VIEWPORT_HEIGHT = 1280;

    //Game State when game is launched
    public static String gameState = "active";

    //Initialize Player Position
    public static final int pos1 = (VIEWPORT_WIDTH/3)/2;
    public static final int pos2 = (VIEWPORT_WIDTH/3)*2 - pos1;
    public static final int pos3 = VIEWPORT_WIDTH - pos1;
    public static int PLAYER_POS_X = pos2;
    public static final int PLAYER_POS_Y = 50;

    //Initialize Apple Position
    public static int Fruit_POS_X = 135;
    public static int Fruit_POS_Y = VIEWPORT_HEIGHT-400;
    public static final int Fruit_speed = 20;

    //Initialize Player setting
    public static int HP = 3;
    public static int score = 0;

    //Initialize to original game state
    public static void reset() {
        HP = 3;
        score = 0;
        gameState = "active";
    }

    //Initialize to fruit state
    public static void fruitReset() {
        Fruit_POS_Y = VIEWPORT_HEIGHT-400;
    }
}
