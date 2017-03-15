package com.mystic.game;

//Core Library
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

//Input Library such as accelerometer
import com.badlogic.gdx.Input;

//Button Library
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

//Graphical library
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Gavin
 */
public class MyGdxGame extends ApplicationAdapter {

    //Initialize Font creator, with Different Font
    private BitmapFont font;
    private FreeTypeFontGenerator fontGenerator;
    private FreeTypeFontGenerator.FreeTypeFontParameter fontParams;

    //Button Setting
    Button btnRestart, btnEnd;
    TextButton.TextButtonStyle textButtonStyle;

    //Initialize Graphics
    private OrthographicCamera cam;
    private Viewport viewport;

    //Declare Renderer and Sprite
    SpriteBatch batch;
    Sprite dog, fruit;
    Texture dogImg, fruitImg, heart, bg;

    @Override
    public void create() {
        //Initialize renderer and font render
        batch = new SpriteBatch();
        font = new BitmapFont();

        //Initialize Graphics
        cam = new OrthographicCamera(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
        Viewport viewport = new FitViewport(gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT, cam);
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

        //Create Sprites
        dog = new Sprite(dogImg);
        fruit = new Sprite(fruitImg);

        /*btnRestart = new TextButton("TRY AGAIN", textButtonStyle);
        btnRestart.addListener( new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
                Gdx.app.log("MyTag", "entered");
                gameSetting.reset();
            }
        });*/
    }

    @Override
    public void render() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        if (gameSetting.gameState == "active") {
            //Get Accelerometer value for dog position
            int accelX = (int) Gdx.input.getAccelerometerX();

            //Clear Render to default Value
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            //Start Render
            batch.begin();

            //Render Background
            //batch.draw(bg, 0, 0, gameSetting.VIEWPORT_WIDTH, gameSetting.VIEWPORT_HEIGHT);
            batch.draw(bg,0,0);
            //Gdx.app.log("Width", Integer.toString(Gdx.app.getGraphics().getWidth()));
            //Gdx.app.log("Height", Integer.toString(Gdx.app.getGraphics().getHeight()));
            //btnRestart.draw(batch,1.0f);

            //Render Top bar( HP, Timer , Points)
            font.draw(batch, "HP:", 10, gameSetting.VIEWPORT_HEIGHT - 25);
            for (int i = 1; i <= gameSetting.HP; i++)
                batch.draw(heart, 140 + (i - 1) * 118, gameSetting.VIEWPORT_HEIGHT - 120);
            //font.draw(batch, "Time:", gameSetting.VIEWPORT_WIDTH/2, gameSetting.VIEWPORT_HEIGHT - 25);
            //font.draw(batch, "60", gameSetting.VIEWPORT_WIDTH/2 + 30, gameSetting.VIEWPORT_HEIGHT - 110);
            font.draw(batch, "Point:", gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 25);
            font.draw(batch, Integer.toString(gameSetting.score), gameSetting.VIEWPORT_WIDTH - 240, gameSetting.VIEWPORT_HEIGHT - 110);

            //Render Fruit - Apple Sprite( Random 3 position)
            batch.draw(fruit, 100, gameSetting.Fruit_POS_Y);
            batch.draw(fruit, 600, gameSetting.Fruit_POS_Y);
            batch.draw(fruit, 1100, gameSetting.Fruit_POS_Y);

            //Process Gravity Droprate
            gameSetting.Fruit_POS_Y -= gameSetting.Fruit_speed;
            if (gameSetting.Fruit_POS_Y < 0)
                gameSetting.fruitReset();

            //Render Player - Dog Sprites
            batch.draw(dog, gameSetting.PLAYER_POS_X, gameSetting.PLAYER_POS_Y);
            if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer)) {
                //Logging test
                Gdx.app.log("MyTag", Integer.toString(gameSetting.VIEWPORT_WIDTH));
                font.draw(batch, Integer.toString(gameSetting.VIEWPORT_WIDTH), 900, 900);
                font.draw(batch, Integer.toString(gameSetting.PLAYER_POS_X), 700, 900);
                font.draw(batch, Integer.toString(accelX), 400, 900);
                //Accel Turn Left
                if (accelX > 7) {
                    gameSetting.PLAYER_POS_X=gameSetting.pos1;
                }
                //Accel Turn Right
                if (accelX < -7) {
                    gameSetting.PLAYER_POS_X=gameSetting.pos1;
                }
                gameSetting.score += 1;
            }
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
            batch.end();
        }
    }
}
