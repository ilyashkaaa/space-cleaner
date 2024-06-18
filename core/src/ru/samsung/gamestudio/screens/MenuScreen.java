package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.AnimatedSprite;
import ru.samsung.gamestudio.GameDifficulty;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;

import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;

public class MenuScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleView;
    ButtonView startButtonView;
    ButtonView weaponButtonView;
    ButtonView settingsButtonView;
    ButtonView exitButtonView;
    AnimatedSprite catGif;
    Texture[] framesGif;


    public MenuScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_MENU_IMG_PATH);
        framesGif = new Texture[] {
                new Texture("textures/gifs/cat_gif/0.png"),
                new Texture("textures/gifs/cat_gif/1.png"),
                new Texture("textures/gifs/cat_gif/2.png"),
                new Texture("textures/gifs/cat_gif/3.png"),
        };
        catGif = new AnimatedSprite(framesGif, 10);

        titleView = new TextView(myGdxGame.plushLargeWhiteFont, 120, 800, "Space Cleaner");
        startButtonView = new ButtonView(140, 646, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "Start");
//        weaponButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "Weapon");
        settingsButtonView = new ButtonView(140, 551, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "Settings");
        exitButtonView = new ButtonView(140, 456, 440, 70, myGdxGame.commonBlackFont, GameResources.BUTTON_LONG_BG_IMG_PATH, "Exit");
    }

    @Override
    public void render(float delta) {

        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        titleView.draw(myGdxGame.batch);
        exitButtonView.draw(myGdxGame.batch);
        settingsButtonView.draw(myGdxGame.batch);
        startButtonView.draw(myGdxGame.batch);
        catGif.update(delta);
        catGif.setPosition(110, 50);
        catGif.draw(myGdxGame.batch);
        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (startButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameSession.difficulty = GameDifficulty.EASY;
                myGdxGame.setScreen(myGdxGame.levelScreen);

            }
            if (exitButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                Gdx.app.exit();
            }
            if (settingsButtonView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.settingsScreen);
            }
        }
    }
}
