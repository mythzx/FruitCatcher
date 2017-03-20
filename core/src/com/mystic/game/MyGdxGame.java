package com.mystic.game;

//Core Library
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

//Data Structure Library
import com.badlogic.gdx.scenes.scene2d.ui.List;

//Input Library such as accelerometer
import com.badlogic.gdx.Input;

//Button Library
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

//Graphical library
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created by Gavin
 */
public class MyGdxGame extends ApplicationAdapter {

    //Accelerometer data filter
    Vector3 raw, gravity, lowpass, highpass;

    //Initialize Font creator, with Different Font
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;

    //Button Setting
    private Button btnRestart, btnEnd;
    TextButton.TextButtonStyle textButtonStyle;

    //Initialize Graphics
    private OrthographicCamera cam;
    private Viewport viewport;

    //Declare Renderer and Sprite
    private SpriteBatch batch;
    //private Sprite dog, fruit;
    private Texture dogImg, fruitImg, heart, bg;

    //Initialize Game Object
     static ArrayList<gameObject> gameObjects;

    public MyGdxGame() {
        //Initialize Game Object
        gameObjects = new ArrayList<gameObject>();

    }
    public void init(){
        MyGdxGame m = new MyGdxGame();
        gameObject dog = new gameObject("dog",gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
        gameObjects.add(dog);

        Gdx.app.log("MyTag", Integer.toString(gameObjects.get(0).getPosX()));

        gameObject apple = new gameObject("apple", gameSetting.Fruit_POS_X1, gameSetting.Fruit_POS_Y);
        gameObjects.add(apple);

        Gdx.app.log("MyTag", Integer.toString(gameObjects.get(1).getPosX()));
    }

    @Override
    public void create() {
        //Initialize renderer and font render
        batch = new SpriteBatch();
        font = new BitmapFont();

        //Initialize Graphics
        cam = new OrthographicCamera(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
        viewport = new StretchViewport(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth()/2, viewport.getWorldHeight()/2, 0);

        //Setting for fonts
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = 160;
        fontParams.color = Color.BLACK;
        font = fontGenerator.generateFont(fontParams);

        // button style
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.CYAN;
        textButtonStyle.overFontColor = Color.RED;

        //Load Images
        bg = new Texture("garden.png");
        dogImg = new Texture("dog.png");
        fruitImg = new Texture("apple.png");
        heart = new Texture("heart.png");


        //create sprites here
        init();

        //Create Sprites
        //dog = new Sprite(dogImg);
        //fruit = new Sprite(fruitImg);

        btnRestart = new TextButton("TRY AGAIN", textButtonStyle);
        btnRestart.addListener( new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Gdx.app.log("MyTag", "entered");
                gameSetting.reset();
            }
        });
    }

    @Override
    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        if (gameSetting.gameState == "active") {
            //Get Accelerometer value for dog position
            int accelX = (int) Gdx.input.getAccelerometerX();
           /* int accelY = (int) Gdx.input.getAccelerometerY();
            int accelZ = (int) Gdx.input.getAccelerometerZ();

            raw = new Vector3 (accelX, accelY, accelZ);
            // low-pass filter (weighted cumulative average)
            gravity = 0.2f * raw + 0.8f * gravity;
            lowpass = gravity;
            // high-pass filter
            highpass = raw – lowpass;*/

            //Clear Render to default Value
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //Start Render
            batch.begin();

            //Render Background
            batch.draw(bg, 0, 0, gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);

            //Render Top bar( HP, Timer , Points)
            font.draw(batch, "HP:", 10, gameSetting.VIEWPORT_HEIGHT - 25);
            for (int i = 1; i <= gameSetting.HP; i++)
                batch.draw(heart, 140 + (i - 1) * 118, gameSetting.VIEWPORT_HEIGHT - 120);
            //font.draw(batch, "Time:", gameSetting.VIEWPORT_WIDTH/2, gameSetting.VIEWPORT_HEIGHT - 25);
            //font.draw(batch, "60", gameSetting.VIEWPORT_WIDTH/2 + 30, gameSetting.VIEWPORT_HEIGHT - 110);
            font.draw(batch, "Point:", gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 25);
            font.draw(batch, Integer.toString(gameSetting.score), gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 110);

            batch.draw(gameObjects.get(0).getSprite(),100,gameObjects.get(0).getPosY());
            Gdx.app.log("game obj 1", Integer.toString(gameObjects.get(0).getPosY()));
            batch.draw(gameObjects.get(1).getSprite(),gameObjects.get(1).getPosX(),gameObjects.get(1).getPosY());
            Gdx.app.log("game obj 2", Integer.toString(gameObjects.get(1).getPosY()));
            //Test Render
            //Gdx.app.log("MyTag", Integer.toString(gameObjects.size()));
            /*for (int i=0; i<gameObjects.size(); i++)
            {
                batch.draw(gameObjects.get(i).getSprite(),gameObjects.get(i).getPosX(),gameObjects.get(i).getPosY());
            }*/


            //Render Fruit - Apple Sprite( Random 3 position)
            /*batch.draw(fruit, gameSetting.Fruit_POS_X1, gameSetting.Fruit_POS_Y);
            batch.draw(fruit, gameSetting.Fruit_POS_X2, gameSetting.Fruit_POS_Y);
            batch.draw(fruit, gameSetting.Fruit_POS_X3, gameSetting.Fruit_POS_Y);
            */

            //Process Gravity Droprate
            /*gameSetting.Fruit_POS_Y -= gameSetting.Fruit_speed;
            if (gameSetting.Fruit_POS_Y < 0){
                gameSetting.fruitReset();
                //gameSetting.HP--;
            }*/

            //Render Player - Dog Sprites
            //batch.draw(dog, gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
            /*if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                //Logging test
                //Gdx.app.log("MyTag", Integer.toString(gameSetting.VIEWPORT_WIDTH));
                //Gdx.app.log("Height", Integer.toString(gameSetting.VIEWPORT_HEIGHT));
                font.draw(batch, Integer.toString(gameSetting.Fruit_POS_X1), 400, 900);
                font.draw(batch, Integer.toString(gameSetting.Fruit_POS_X2), 700, 900);
                font.draw(batch, Integer.toString(gameSetting.Fruit_POS_X3), 900, 900);

                if (accelX > 4) {//Accel Turn Left
                    gameSetting.PLAYER_POS_X = gameSetting.pos1;
                } else if (accelX < -4) {//Accel Turn Right
                    gameSetting.PLAYER_POS_X = gameSetting.pos3;
                } else {
                    gameSetting.PLAYER_POS_X = gameSetting.pos2;
                }
                gameSetting.score += 1;
            }*/
            //End of Graphic Rendering
            batch.end();
            //Check Hp = 0 to see if GAME OVER
            if (gameSetting.HP == 0)
                gameSetting.gameState = "over";
        }

        //Game over state
        if (gameSetting.gameState == "over") {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(bg, 0, 0, gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
            font.draw(batch, "Game over", 400, 900);
            font.draw(batch, "Game over"+ gameSetting.score, 400, 1300);
            btnRestart.draw(batch,1.0f);
            batch.end();
        }
    }
}
