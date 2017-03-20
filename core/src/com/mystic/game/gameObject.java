package com.mystic.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Gavin
 */

public class gameObject {
    //Declaration of Object
    private Sprite s;
    private Vector2 v;
    private float radius;

    //Load Dog and Apple Image
    private Texture dogImg = new Texture("dog.png");
    private Texture fruitImg = new Texture("apple.png");

    //Object Instance
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

    Sprite getSprite(){
        return s;
    }
    void setPosition(int x, int y){
        v.set(x,y);
    }
    int getPosX(){
        return (int)v.x;
    }
    int getPosY(){
        return (int)v.y;
    }
    boolean checkApple()
    {
        if(getPosY()<0)
            return true;
        return false;
    }
}
