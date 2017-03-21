package com.mystic.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gavin
 */

public class gameObject {
    //Declaration of Object
    private Sprite s;
    private Vector2 v;
    private float radius;
    private Circle c;

    //Load Dog and Apple Image
    private Texture dogImg = new Texture("dog.png");
    private Texture fruitImg = new Texture("apple.png");

    //Object Instance
    public gameObject(String name, int x, int y) {
        if (name.equals("dog")) {
            s = new Sprite(dogImg);
        }
        if (name.equals("apple")) {
            s = new Sprite(fruitImg);
        }
        v = new Vector2(x, y);
        radius = s.getHeight()/2f;
    }

    Sprite getSprite() {
        return s;
    }

    void setPosition(int x, int y) {
        v.set(x, y);
    }

    int getPosX() {
        return (int) v.x;
    }

    int getPosY() {
        return (int) v.y;
    }

    boolean checkApple() {
        return getPosY() < 0;
    }

    boolean checkCollision(gameObject e) {
        return e.radius + radius > v.dst(e.v);
    }
}
