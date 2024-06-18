package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;

import java.util.ArrayList;

public class SettingsScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;

    MovingBackgroundView backgroundView;
    TextView titleTextView;
    ImageView blackoutImageView;
    ButtonView returnButton;
    TextView musicSettingView;
    TextView soundSettingView;
    TextView clearSettingView;
    TextView gyroscopeSettingView;

    public SettingsScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        titleTextView = new TextView(myGdxGame.largeWhiteFont, 256, 956, "Settings");
        blackoutImageView = new ImageView(85, 365, GameResources.BLACKOUT_MIDDLE_IMG_PATH);
        clearSettingView = new TextView(myGdxGame.commonWhiteFont, 173, 649, "Clear records");


        musicSettingView = new TextView(
                myGdxGame.commonWhiteFont,
                173, 767,
                "Music: " + translateStateToText(MemoryManager.loadIsMusicOn())
        );

        soundSettingView = new TextView(
                myGdxGame.commonWhiteFont,
                173, 708,
                "Sound: " + translateStateToText(MemoryManager.loadIsSoundOn())
        );

        returnButton = new ButtonView(
                280, 447,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Return"
        );
        gyroscopeSettingView = new TextView(
                myGdxGame.commonWhiteFont,
                173, 590,
                "Gyroscope: " + translateStateToText(MemoryManager.loadIsGyroscopeOn())
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
        titleTextView.draw(myGdxGame.batch);
        blackoutImageView.draw(myGdxGame.batch);
        returnButton.draw(myGdxGame.batch);
        musicSettingView.draw(myGdxGame.batch);
        soundSettingView.draw(myGdxGame.batch);
        clearSettingView.draw(myGdxGame.batch);
        gyroscopeSettingView.draw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (clearSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveTableOfEasyRecords(new ArrayList<>());
                MemoryManager.saveTableOfMediumRecords(new ArrayList<>());
                MemoryManager.saveTableOfHardRecords(new ArrayList<>());
                clearSettingView.setText("Clear records (cleared)");
            }
            if (musicSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveMusicSettings(!MemoryManager.loadIsMusicOn());
                musicSettingView.setText("Music: " + translateStateToText(MemoryManager.loadIsMusicOn()));
                myGdxGame.audioManager.updateMusicFlag();
            }
            if (soundSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveSoundSettings(!MemoryManager.loadIsSoundOn());
                soundSettingView.setText("Sound: " + translateStateToText(MemoryManager.loadIsSoundOn()));
                myGdxGame.audioManager.updateSoundFlag();
            }
            if (gyroscopeSettingView.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                MemoryManager.saveGyroscopeSettings(!MemoryManager.loadIsGyroscopeOn());
                gyroscopeSettingView.setText("Gyroscope: " + translateStateToText(MemoryManager.loadIsGyroscopeOn()));
                myGdxGame.gyroscope.updateGyroscopeFlag();

            }

        }
    }

    private String translateStateToText(boolean state) {
        return state ? "ON" : "OFF";
    }
}
