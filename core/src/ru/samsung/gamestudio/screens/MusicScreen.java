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
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.AudioManager;

public class MusicScreen extends ScreenAdapter {
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView textView;
    Texture frameTexture;
    ButtonView music1;
    ButtonView music2;
    ButtonView music3;
    ButtonView music4;
    ButtonView music5;
    ButtonView music6;
    ButtonView returnButton;
    ButtonView nextButton;
    Texture widePutin, nyanCat, otVinta, amogus, basic, maxwellTheCat;
    TextView widePutinText, nyanCatText, otVintaText, amogusText, basicText, maxwellTheCatText;
    AnimatedSprite pianoCat;

    public MusicScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        textView = new TextView(myGdxGame.largeWhiteFont, 200, 1200, "Select music");
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_EARTH_IMG_PATH);
        frameTexture = new Texture(GameResources.FRAME_FOR_MUSIC_BUTTON);
        nyanCat = new Texture(GameResources.NYAN_CAT);
        widePutin = new Texture(GameResources.WIDE_PUTIN_IMG);
        otVinta = new Texture(GameResources.OT_VINTA_IMG);
        amogus = new Texture(GameResources.AMOGUS_MUSIC_IMG);
        basic = new Texture(GameResources.BASIC_MUSIC_IMG);
        maxwellTheCat = new Texture(GameResources.MAXWELL_THE_CAT);
        pianoCat = new AnimatedSprite(GameResources.PIANO_CAT, 10);

        basicText = new TextView(myGdxGame.plushLargeWhiteFont, 200, 1070, "Basic music(");
        nyanCatText = new TextView(myGdxGame.plushLargeWhiteFont, 260, 913, "Nyan Cat");
        widePutinText = new TextView(myGdxGame.plushLargeWhiteFont, 240, 756, "Wide Putin");
        amogusText = new TextView(myGdxGame.plushLargeWhiteFont, 290, 599, "Amogus");
        otVintaText = new TextView(myGdxGame.plushLargeWhiteFont, 285, 442, "OT VINTA");
        maxwellTheCatText = new TextView(myGdxGame.plushMiddleWhiteFont, 220, 295, "Maxwell the cat");



        music1 = new ButtonView(37, 1025, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
        music2 = new ButtonView(37, 868, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
        music3 = new ButtonView(37, 711, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
        music4 = new ButtonView(37, 554, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
        music5 = new ButtonView(37, 397, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
        music6 = new ButtonView(37, 240, 646, 145, GameResources.BUTTON_MUSIC_BG_IMG_PATH);
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
                "Next");


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
        music1.draw(myGdxGame.batch);
        music2.draw(myGdxGame.batch);
        music3.draw(myGdxGame.batch);
        music4.draw(myGdxGame.batch);
        music5.draw(myGdxGame.batch);
        music6.draw(myGdxGame.batch);
        nextButton.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        frameDraw(myGdxGame.batch);
        myGdxGame.batch.draw(basic, 67, 1035, 120, 120);
        myGdxGame.batch.draw(nyanCat, 67, 878, 120, 120);
        myGdxGame.batch.draw(widePutin, 67, 721, 120, 120);
        myGdxGame.batch.draw(amogus, 67, 564, 120, 120);
        myGdxGame.batch.draw(otVinta, 67, 407, 120, 120);
        myGdxGame.batch.draw(maxwellTheCat, 67, 250, 120, 120);

        pianoCat.update(delta);
        pianoCat.setPosition(-100, -50);
        pianoCat.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);

        basicText.draw(myGdxGame.batch);
        nyanCatText.draw(myGdxGame.batch);
        widePutinText.draw(myGdxGame.batch);
        amogusText.draw(myGdxGame.batch);
        otVintaText.draw(myGdxGame.batch);
        maxwellTheCatText.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.levelScreen);
            }
            if (nextButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.starshipScreen);
            }
            if (music6.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/maxwell_the_cat.mp3";
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/maxwell_the_cat.mp3"));
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic.setLooping(true);
                AudioManager.backgroundMusic.play();
            }
            if (music2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/nyan_cat.mp3";
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/nyan_cat.mp3"));
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic.setLooping(true);
                AudioManager.backgroundMusic.play();
            }
            if (music3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/wide_putin.mp3";
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/wide_putin.mp3"));
                AudioManager.backgroundMusic.setLooping(true);

                AudioManager.backgroundMusic.play();
            }
            if (music4.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/amogus.mp3";
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/amogus.mp3"));
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic.setLooping(true);


                AudioManager.backgroundMusic.play();
            }
            if (music5.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/ot_vinta.mp3";
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/ot_vinta.mp3"));
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic.setLooping(true);

                AudioManager.backgroundMusic.play();
            }
            if (music1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                AudioManager.backgroundMusic.stop();
                GameResources.BACKGROUND_MUSIC_PATH = "sounds/background_music.mp3";
                AudioManager.backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/background_music.mp3"));
                AudioManager.backgroundMusic.setVolume(0.2f);
                AudioManager.backgroundMusic.setLooping(true);
                AudioManager.backgroundMusic.play();
            }
        }
    }

    private void frameDraw(SpriteBatch batch) {
        switch (GameResources.BACKGROUND_MUSIC_PATH) {
            case "sounds/background_music.mp3":
                batch.draw(frameTexture, 37, 1025);
                break;
            case "sounds/nyan_cat.mp3":
                batch.draw(frameTexture, 37, 868);
                break;
            case "sounds/wide_putin.mp3":
                batch.draw(frameTexture, 37, 711);
                break;
            case "sounds/amogus.mp3":
                batch.draw(frameTexture, 37, 554);
                break;
            case "sounds/ot_vinta.mp3":
                batch.draw(frameTexture, 37, 397);
                break;
            case "sounds/maxwell_the_cat.mp3":
                batch.draw(frameTexture, 37, 240);
                break;
        }
    }
}
