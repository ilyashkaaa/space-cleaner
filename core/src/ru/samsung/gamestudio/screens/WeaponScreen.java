package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.Image;

import ru.samsung.gamestudio.AnimatedSprite;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ChooseButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;

public class WeaponScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView textView;
    Texture frameTexture;
    ChooseButtonView basicGunButton;
    ChooseButtonView laserGunButton;
    ChooseButtonView starGunButton;
    ChooseButtonView toxicGunButton;
    ChooseButtonView iceGunButton;
    ChooseButtonView energyGunButton;
    ButtonView returnButton;
    ButtonView nextButton;

    ImageView catImage;

    public WeaponScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_EARTH_IMG_PATH);
        textView = new TextView(myGdxGame.largeWhiteFont, 180, 1200, "Select weapon");

        frameTexture = new Texture(GameResources.FRAME_FOR_SQUARE_BUTTON);
        catImage = new ImageView(60, 200, GameResources.KOSMOCAT_PATH);
        basicGunButton = new ChooseButtonView(60, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/basicImage.png", "Basic Gun");
        laserGunButton = new ChooseButtonView(280, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/laserImage.png", "Laser Gun");
        starGunButton = new ChooseButtonView(500, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/starImage.png", "Star Gun");
        toxicGunButton = new ChooseButtonView(60, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/toxicImage.png", "Toxic Gun");
        iceGunButton = new ChooseButtonView(280, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/iceImage.png", "Ice Gun");
        energyGunButton = new ChooseButtonView(500, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/bulletImages/energyImage.png", "Energy Gun");
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
                "Next"
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
        basicGunButton.draw(myGdxGame.batch);
        laserGunButton.draw(myGdxGame.batch);
        starGunButton.draw(myGdxGame.batch);
        toxicGunButton.draw(myGdxGame.batch);
        iceGunButton.draw(myGdxGame.batch);
        energyGunButton.draw(myGdxGame.batch);
        catImage.draw(myGdxGame.batch);


        returnButton.draw(myGdxGame.batch);
        nextButton.draw(myGdxGame.batch);
        frameDraw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (basicGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/basicBullet.png";
            }
            if (laserGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/laserBullet.png";
            }
            if (starGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/starBullet.png";
            }
            if (iceGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/iceBullet.png";
            }
            if (toxicGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/toxicBullet.png";
            }
            if (energyGunButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameResources.BULLET_IMG_PATH = "textures/bullets/energyBullet.png";
            }
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.starshipScreen);
            }
            if (nextButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                if (Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope)) {
                    myGdxGame.setScreen(myGdxGame.controlScreen);
                } else {
                    if (MemoryManager.loadIsGyroscopeOn()) {
                        MemoryManager.saveGyroscopeSettings(!MemoryManager.loadIsGyroscopeOn());
                    }
                    myGdxGame.setScreen(myGdxGame.gameScreen);
                }

            }
        }
    }

    private void frameDraw(SpriteBatch batch) {
        switch (GameResources.BULLET_IMG_PATH) {
            case "textures/bullets/basicBullet.png":
                batch.draw(frameTexture, 54, 994);
                break;
            case "textures/bullets/laserBullet.png":
                batch.draw(frameTexture, 274, 994);
                break;
            case "textures/bullets/starBullet.png":
                batch.draw(frameTexture, 494, 994);
                break;
            case "textures/bullets/toxicBullet.png":
                batch.draw(frameTexture, 54, 774);
                break;
            case "textures/bullets/iceBullet.png":
                batch.draw(frameTexture, 274, 774);
                break;
            case "textures/bullets/energyBullet.png":
                batch.draw(frameTexture, 494, 774);
                break;
        }
    }

}
