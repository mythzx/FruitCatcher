package com.mystic.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gavin
 */

public class gameObject {
    static Sprite s;
    static Vector2 v;
    static float radius;

    static Texture dogImg = new Texture("dog.png");
    static  Texture fruitImg = new Texture("apple.png");

    public gameObject(String name,int x,int y){
        if(name.equals("dog")){
            s = new Sprite(dogImg);
        }
        if(name.equals("apple")){
            s = new Sprite(fruitImg);
        }
        v = new Vector2(x,y);
        radius = 1f;
    }

    static Sprite getSprite(){
        return s;
    }

    static void setPosition(int x, int y){
        v.set(x,y);
    }
    static int getPosX(){
        return (int)v.x;
    }
    static int getPosY(){
        return (int)v.y;
    }
    static boolean checkCollide()
    {
        return true;
    }
}
