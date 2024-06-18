package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Collections;


import ru.samsung.gamestudio.GameDifficulty;
import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;

public class LevelScreen extends ScreenAdapter {
    ButtonView easyLevelButton;
    ButtonView mediumLevelButton;
    ButtonView hardLevelButton;
    ButtonView returnButton;
    ButtonView nextButton;
    Texture frameTexture;
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    ImageView easyImage, mediumImage, hardImage;
    TextView easyText, mediumText, hardText, selectDifficultyText;
    TextView easyDescriptionText, mediumDescriptionText, hardDescriptionText;
    TextView easyRecordText, mediumRecordText, hardRecordText;

    public LevelScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_EARTH_IMG_PATH
        );
        frameTexture = new Texture(GameResources.FRAME_FOR_LEVEL_BUTTON);
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
        selectDifficultyText = new TextView(myGdxGame.largeWhiteFont, 175, 1200, "Select difficulty");
        easyLevelButton = new ButtonView(37, 846, 646, 312, GameResources.BUTTON_LEVEL_BG_IMG_PATH);
        mediumLevelButton = new ButtonView(37, 504, 646, 312, GameResources.BUTTON_LEVEL_BG_IMG_PATH);
        hardLevelButton = new ButtonView(37, 162, 646, 312, GameResources.BUTTON_LEVEL_BG_IMG_PATH);
        easyImage = new ImageView(70, 939, GameResources.EASY_IMG_PATH);
        mediumImage = new ImageView(70, 597, GameResources.MEDIUM_IMG_PATH);
        hardImage = new ImageView(70, 255, GameResources.HARD_IMG_PATH);
        easyText = new TextView(myGdxGame.largeWhiteFont, 370, 1095, "EASY");
        mediumText = new TextView(myGdxGame.largeWhiteFont, 330, 753, "MEDIUM");
        hardText = new TextView(myGdxGame.largeWhiteFont, 360, 411, "HARD");
        easyDescriptionText = new TextView(myGdxGame.middleWhiteFont, 280, 1000, "Difficulty for noobs.");
        mediumDescriptionText = new TextView(myGdxGame.middleWhiteFont, 270, 638, "Enemies appear more\nfrequently over time.");
        hardDescriptionText = new TextView(myGdxGame.middleWhiteFont, 240, 280, "Enemies appear more\nfrequently over time and \nall boosts are disabled.");
    }

    @Override
    public void render(float delta) {
        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        easyLevelButton.draw(myGdxGame.batch);
        mediumLevelButton.draw(myGdxGame.batch);
        hardLevelButton.draw(myGdxGame.batch);
        easyImage.draw(myGdxGame.batch);
        mediumImage.draw(myGdxGame.batch);
        hardImage.draw(myGdxGame.batch);
        easyText.draw(myGdxGame.batch);
        mediumText.draw(myGdxGame.batch);
        hardText.draw(myGdxGame.batch);
        easyDescriptionText.draw(myGdxGame.batch);
        mediumDescriptionText.draw(myGdxGame.batch);
        hardDescriptionText.draw(myGdxGame.batch);
        selectDifficultyText.draw(myGdxGame.batch);
        easyRecordText = new TextView(myGdxGame.middleWhiteFont, 300, 920, "Best record: " + maxOfList(MemoryManager.loadEasyRecordsTable()));
        mediumRecordText = new TextView(myGdxGame.middleWhiteFont, 300, 568, "Best record: " + maxOfList(MemoryManager.loadMediumRecordsTable()));
        hardRecordText = new TextView(myGdxGame.middleWhiteFont, 300, 216, "Best record: " + maxOfList(MemoryManager.loadHardRecordsTable()));
        easyRecordText.draw(myGdxGame.batch);
        mediumRecordText.draw(myGdxGame.batch);
        hardRecordText.draw(myGdxGame.batch);


        returnButton.draw(myGdxGame.batch);
        nextButton.draw(myGdxGame.batch);
        frameDraw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.menuScreen);
            }
            if (nextButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.musicScreen);
            }
            if (easyLevelButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameSession.difficulty = GameDifficulty.EASY;

            }
            if (mediumLevelButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameSession.difficulty = GameDifficulty.MEDIUM;
            }
            if (hardLevelButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                GameSession.difficulty = GameDifficulty.HARD;
            }

        }
    }

    private void frameDraw(SpriteBatch batch) {
        switch (GameSession.difficulty) {
            case EASY:
                batch.draw(frameTexture, 37, 846);
                break;
            case MEDIUM:
                batch.draw(frameTexture, 37, 504);
                break;
            case HARD:
                batch.draw(frameTexture, 37, 162);
                break;

        }
    }

    private int maxOfList(ArrayList<Integer> myList) {
        if (myList == null ) {
            return 0;
        } else {
            if (!myList.isEmpty()) {
                int maximum = myList.get(0);
                for (int i = 1; i < myList.size(); i++) {
                    if (maximum < myList.get(i))
                        maximum = myList.get(i);
                }
                return maximum;
            } else {
                return 0;
            }
        }


    }
}

