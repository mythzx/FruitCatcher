package com.mystic.game;

/**
 * Created by Gavin
 */

class gameSetting {
    //Fixed Android Screen Width and Height
    static final int VIEWPORT_WIDTH = 1440;
    static final int VIEWPORT_HEIGHT = 2392;

    //Game State when game is launched
    static String gameState = "active";

    //Initialize Player Position
    static final int pos1 = (VIEWPORT_WIDTH / 3) / 2 - 142;
    static final int pos2 = 2 * (VIEWPORT_WIDTH / 3) - (VIEWPORT_WIDTH / 3) / 2 - 142;
    static final int pos3 = VIEWPORT_WIDTH - (VIEWPORT_WIDTH / 3) / 2 - 142;
    static int PLAYER_POS_X = pos2;
    static final int PLAYER_POS_Y = 50;

    //Initialize Apple Position
    static final int Fruit_POS_X1 = (VIEWPORT_WIDTH / 3) / 2 - 79;
    static final int Fruit_POS_X2 = 2 * (VIEWPORT_WIDTH / 3) - (VIEWPORT_WIDTH / 3) / 2 - 79;
    static final int Fruit_POS_X3 = VIEWPORT_WIDTH - (VIEWPORT_WIDTH / 3) / 2 - 79;
    static int Fruit_POS_Y = VIEWPORT_HEIGHT - 400;
    static final int Fruit_speed = 20;

    //Initialize Player setting
    static int HP = 3;
    static int score = 0;

    //Initialize to original game state
    static void reset() {
        HP = 3;
        score = 0;
        gameState = "active";
    }

    //Initialize to fruit state
    static void fruitReset() {
        Fruit_POS_Y = VIEWPORT_HEIGHT - 400;
    }
}
