package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.AnimatedSprite;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ChooseButtonView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;

public class ControlScreen extends ScreenAdapter {
    ChooseButtonView touchButton;
    ChooseButtonView gyroscopeButton;
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView textView;
    Texture frameTexture;
    ButtonView returnButton;
    ButtonView nextButton;
    AnimatedSprite catPlayGif;

    public ControlScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        catPlayGif = new AnimatedSprite(GameResources.CAT_PLAY_TEXTURES, 10);
        touchButton = new ChooseButtonView(170, 560, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/touchImage.png", "Touch");
        gyroscopeButton = new ChooseButtonView(390, 560, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/gyroscopeImage.png", "Gyroscope");
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_EARTH_IMG_PATH);
        textView = new TextView(myGdxGame.plushMiddleWhiteFont, 180, 1200, "Select control");
        frameTexture = new Texture(GameResources.FRAME_FOR_SQUARE_BUTTON);
        returnButton = new ButtonView(
                330, 40,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Return");
        nextButton = new ButtonView(
                530, 40, 160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Play!"
        );
    }

    @Override
    public void render(float delta) {

        handleInput();

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();

        backgroundView.draw(myGdxGame.batch);
        textView.draw(myGdxGame.batch);
        gyroscopeButton.draw(myGdxGame.batch);
        touchButton.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        nextButton.draw(myGdxGame.batch);
        frameDraw(myGdxGame.batch);
        catPlayGif.update(delta);
        catPlayGif.setPosition(200, 800);
        catPlayGif.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.weaponScreen);
            }
            if (nextButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.gameScreen);
            }
            if (touchButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (MemoryManager.loadIsGyroscopeOn()) {
                    MemoryManager.saveGyroscopeSettings(!MemoryManager.loadIsGyroscopeOn());
                    myGdxGame.gyroscope.updateGyroscopeFlag();
                }
            }
            if (gyroscopeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (!MemoryManager.loadIsGyroscopeOn()) {
                    MemoryManager.saveGyroscopeSettings(!MemoryManager.loadIsGyroscopeOn());
                    myGdxGame.gyroscope.updateGyroscopeFlag();
                }

            }
        }
    }

    private void frameDraw(SpriteBatch batch) {
        if (MemoryManager.loadIsGyroscopeOn()) {
            batch.draw(frameTexture, 384, 554);
        } else {
            batch.draw(frameTexture, 164, 554);
        }
    }
}


