package com.mystic.game;

//Core Library

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

//Input Library such as accelerometer
import com.badlogic.gdx.Input;

//Button Library
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

//Graphical library
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

//Data Structure Library
import java.util.ArrayList;
import java.util.List;

//Random Generator Library
import java.util.Random;

/**
 * Created by Gavin
 */
public class MyGdxGame extends ApplicationAdapter {

    //Initialize Font creator, with Different Font
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;

    //Button Setting
    private TextButton btnRestart, btnEnd, btnLeft, btnRight;
    private TextButton.TextButtonStyle textButtonStyle;

    //Layout Structure
    private Stage stage, stageMain;
    private Table table, tableControl;

    //Initialize Graphics
    private OrthographicCamera cam;
    private Viewport viewport;

    //Declare Renderer and background
    private SpriteBatch batch;
    private Texture heart, bg;
    private Label lbGame, lbMsg, lbScore;

    //Initialize Game Object
    private List<gameObject> gameObjects = new ArrayList<gameObject>();

    private void init() { // Load Player
        gameObject dog = new gameObject("dog", gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
        gameObjects.add(dog);
    }

    private void spawner() {//Generate random apple sprites
        int no = new Random().nextInt(4);
        switch (no) {
            case 1:
                gameObject apple = new gameObject("apple", gameSetting.Fruit_POS_X1, gameSetting.Fruit_POS_Y);
                gameObjects.add(apple);
                break;
            case 2:
                gameObject apple1 = new gameObject("apple", gameSetting.Fruit_POS_X2, gameSetting.Fruit_POS_Y);
                gameObjects.add(apple1);
                break;
            case 3:
                gameObject apple2 = new gameObject("apple", gameSetting.Fruit_POS_X3, gameSetting.Fruit_POS_Y);
                gameObjects.add(apple2);
                break;
        }
    }

    @Override
    public void create() {
        //Initialize renderer and font render
        batch = new SpriteBatch();
        font = new BitmapFont();

        //Initialize Graphics
        cam = new OrthographicCamera(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
        viewport = new StretchViewport(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT, cam);
        cam.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);

        //Setting for fonts
        fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/pixel.ttf"));
        fontParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParams.size = 160;
        fontParams.color = Color.BLACK;
        font = fontGenerator.generateFont(fontParams);

        // button style
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.RED;
        textButtonStyle.overFontColor = Color.RED;

        //Load Images
        bg = new Texture("garden.png");
        heart = new Texture("heart.png");

        //Initialize Player Sprite Dog
        init();

        //For Extreme cases -  Chaos mode activated
        //Stage and Listener
        stageMain = new Stage();

        // Layout Button Control
        tableControl = new Table();
        tableControl.bottom();
        tableControl.setCullingArea(new Rectangle(0,0,100,100));

        btnLeft = new TextButton("Left", textButtonStyle);
        btnLeft.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSetting.reset();
            }
        });

        btnRight = new TextButton("Right", textButtonStyle);
        btnRight.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSetting.reset();
            }
        });
        tableControl.add(btnLeft).expandX().align(Align.left).padBottom(100).padLeft(160);
        tableControl.add(btnRight).expandX().align(Align.left).padBottom(100).padLeft(2100);
        stageMain.addActor(tableControl);

        //Stage and Listener
        stage = new Stage();

        // Layout for Elements
        table = new Table();
        table.top();
        table.setFillParent(true);

        //Buttons and Labels
        lbGame = new Label("Game Over", new Label.LabelStyle(font, Color.BLACK));
        lbMsg = new Label("Your score is:", new Label.LabelStyle(font, Color.BLACK));
        lbScore = new Label("", new Label.LabelStyle(font, Color.BLACK));
        btnRestart = new TextButton("TRY AGAIN", textButtonStyle);
        btnRestart.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSetting.reset();
            }
        });

        btnEnd = new TextButton("End Game", textButtonStyle);
        btnEnd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSetting.reset();
            }
        });
        // Adding Elements to table layout
        table.add(lbGame).expandX().align(Align.center).padTop(gameSetting.VIEWPORT_HEIGHT / 2);
        table.row();
        table.add(lbMsg).expandX().align(Align.center).padTop(20);
        table.row();
        table.add(lbScore).align(Align.center).expandX().colspan(2).padTop(10);
        table.row();
        table.add(btnRestart).center().expandX().colspan(2).padTop(20);
        table.row();
        table.add(btnEnd).colspan(2).expandY().align(Align.bottom).padTop(10);

        btnEnd.setVisible(false);
        btnRestart.setVisible(false);
        stage.addActor(table);

    }

    @Override
    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        if (gameSetting.gameState.equals("active")) {
            Gdx.input.setInputProcessor(stageMain);
            //Get Accelerometer value for dog position
            float accelX = Gdx.input.getAccelerometerX();

            //Clear Render to default Value
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //Spawn apple in chaos mode
            if (gameSetting.appleTimer % 30 == 0) {
                spawner();
            }
            gameSetting.appleTimer++;

            //Process Gravity Droprate
            for (int i = 1; i < gameObjects.size(); i++) {
                int apple_x = gameObjects.get(i).getPosX();
                int apple_y = gameObjects.get(i).getPosY();
                if (gameObjects.get(i).checkCollision(gameObjects.get(0))) {
                    gameObjects.remove(i);
                    gameSetting.score++;
                } else {
                    if (gameObjects.get(i).checkApple()) {
                        gameSetting.HP--;
                        gameObjects.remove(i);
                    } else {
                        apple_y -= gameSetting.Fruit_speed;
                        gameObjects.get(i).setPosition(apple_x, apple_y);
                    }
                }
            }

            //Accelerometer for Dog Controls
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                if (accelX > 4f) {//Accel Turn Left
                    gameSetting.PLAYER_POS_X = gameSetting.pos1;
                    gameObjects.get(0).setPosition(gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
                } else if (accelX < -4f) {//Accel Turn Right
                    gameSetting.PLAYER_POS_X = gameSetting.pos3;
                    gameObjects.get(0).setPosition(gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
                } else {
                    gameSetting.PLAYER_POS_X = gameSetting.pos2;
                    gameObjects.get(0).setPosition(gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
                }
            }

            //Start Render
            batch.begin();

            //Render Background
            batch.draw(bg, 0, 0, gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);

            //Render Top bar( HP, Timer , Points)
            font.draw(batch, "HP:", 10, gameSetting.VIEWPORT_HEIGHT - 25);
            for (int i = 1; i <= gameSetting.HP; i++)
                batch.draw(heart, 140 + (i - 1) * 118, gameSetting.VIEWPORT_HEIGHT - 120);
            font.draw(batch, "Point:", gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 25);
            font.draw(batch, Integer.toString(gameSetting.score), gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 110);

            //Render all the game Objects(Dog & apples)
            for (int i = 0; i < gameObjects.size(); i++) {
                batch.draw(gameObjects.get(i).getSprite(), gameObjects.get(i).getPosX(), gameObjects.get(i).getPosY());
            }
            //stageMain.draw(); // Enable for buttons on game page

            //End of Graphic Rendering
            batch.end();
            //Check Hp = 0 to see if GAME OVER
            if (gameSetting.HP == 0)
                gameSetting.gameState = "over";
        }

        //Game over state
        if (gameSetting.gameState.equals("over")) {
            Gdx.input.setInputProcessor(stage);
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            batch.draw(bg, 0, 0, gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
            font.draw(batch, "Game over", 0, 0);
            lbScore.setText(Integer.toString(gameSetting.score));
            btnRestart.setVisible(true);
            btnEnd.setVisible(true);
            stage.draw();
            batch.end();
        }
    }
}
