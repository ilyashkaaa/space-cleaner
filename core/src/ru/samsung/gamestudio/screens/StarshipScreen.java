package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.MyGdxGame;
import ru.samsung.gamestudio.components.ButtonView;
import ru.samsung.gamestudio.components.ChooseButtonView;
import ru.samsung.gamestudio.components.ImageView;
import ru.samsung.gamestudio.components.MovingBackgroundView;
import ru.samsung.gamestudio.components.ShieldView;
import ru.samsung.gamestudio.components.TextView;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.objects.GameObject;
import ru.samsung.gamestudio.objects.ShipObject;

public class StarshipScreen extends ScreenAdapter {
    Texture frameTexture;
    Texture buttonTexture;
    ButtonView returnButton;
    ButtonView nextButton;
    ChooseButtonView ship1;
    ChooseButtonView ship2;
    ChooseButtonView ship3;
    ChooseButtonView ship4;
    ChooseButtonView ship5;
    ChooseButtonView ship6;
    ChooseButtonView ship7;
    ChooseButtonView ship8;
    ChooseButtonView ship9;
    ChooseButtonView ship10;
    MyGdxGame myGdxGame;
    MovingBackgroundView backgroundView;
    TextView text;
    Texture catImage;
    int numberShip;
    int frameCounter;
    int frameMultiplier = 6;

    public StarshipScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        frameTexture = new Texture(GameResources.FRAME_FOR_SQUARE_BUTTON);
        buttonTexture = new Texture(GameResources.BUTTON_SQUARE_BG_IMG_PATH);
        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_EARTH_IMG_PATH);
        catImage = new Texture(GameResources.CAT_CUTE_PATH);

        text = new TextView(myGdxGame.largeWhiteFont, 180, 1200, "Select starship");
        ship1 = new ChooseButtonView(60, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship1/0.png", "Ship 1");
        ship2 = new ChooseButtonView(280, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship2/0.png", "Ship 2");
        ship3 = new ChooseButtonView(500, 1000, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship3/0.png", "Ship 3");
        ship4 = new ChooseButtonView(60, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship4/0.png", "Ship 4");
        ship5 = new ChooseButtonView(280, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship5/0.png", "Ship 5");
        ship6 = new ChooseButtonView(500, 780, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship6/0.png", "Ship 6");
        ship7 = new ChooseButtonView(60, 560, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship7/0.png", "Ship 7");
        ship8 = new ChooseButtonView(280, 560, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship8/0.png", "Ship 8");
        ship9 = new ChooseButtonView(500, 560, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship9/0.png", "Ship 9");
        ship10 = new ChooseButtonView(60, 340, 160, 160, myGdxGame.commonWhiteFont, GameResources.BUTTON_SQUARE_BG_IMG_PATH, "textures/ships/ship10/1.png", "Ship 10");

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
        numberShip = 1;
        frameCounter = 0;

    }

    @Override
    public void render(float delta) {

        handleInput();
        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);
        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        ship1.draw(myGdxGame.batch);
        ship2.draw(myGdxGame.batch);
        ship3.draw(myGdxGame.batch);
        ship4.draw(myGdxGame.batch);
        ship5.draw(myGdxGame.batch);
        ship6.draw(myGdxGame.batch);
        ship7.draw(myGdxGame.batch);
        ship8.draw(myGdxGame.batch);
        ship9.draw(myGdxGame.batch);
        ship10.draw(myGdxGame.batch);
        shipButtonDraw(myGdxGame.batch);
        text.draw(myGdxGame.batch);
        myGdxGame.batch.draw(catImage, 300, 150, 368, 340);


        returnButton.draw(myGdxGame.batch);
        nextButton.draw(myGdxGame.batch);
        frameDraw(myGdxGame.batch);

        myGdxGame.batch.end();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (returnButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.musicScreen);
            }
            if (nextButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                myGdxGame.setScreen(myGdxGame.weaponScreen);
            }
            if (ship1.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP1;
                numberShip = 1;
            }
            if (ship2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP2;
                numberShip = 2;
            }
            if (ship3.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP3;
                numberShip = 3;
            }
            if (ship4.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP4;
                numberShip = 4;
            }
            if (ship5.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP5;
                numberShip = 5;
            }
            if (ship6.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP6;
                numberShip = 6;
            }
            if (ship7.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP7;
                numberShip = 7;
            }
            if (ship8.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP8;
                numberShip = 8;
            }
            if (ship9.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP9;
                numberShip = 9;
            }
            if (ship10.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                ShipObject.framesArray = GameResources.SHIP10;
                numberShip = 10;
            }

        }
    }
    private void frameDraw(SpriteBatch batch) {
        switch (numberShip) {
            case 1:
                batch.draw(frameTexture, 54, 994);
                break;
            case 2:
                batch.draw(frameTexture, 274, 994);
                break;
            case 3:
                batch.draw(frameTexture, 494, 994);
                break;
            case 4:
                batch.draw(frameTexture, 54, 774);
                break;
            case 5:
                batch.draw(frameTexture, 274, 774);
                break;
            case 6:
                batch.draw(frameTexture, 494, 774);
                break;
            case 7:
                batch.draw(frameTexture, 54, 554);
                break;
            case 8:
                batch.draw(frameTexture, 274, 554);
                break;
            case 9:
                batch.draw(frameTexture, 494, 554);
                break;
            case 10:
                batch.draw(frameTexture, 54, 334);
                break;

        }

    }
    private void shipButtonDraw(SpriteBatch batch) {
        switch (numberShip) {
            case 1:
                batch.draw(buttonTexture, 60, 1000);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 60, 1000, 160, 160);
                break;
            case 2:
                batch.draw(buttonTexture, 280, 1000);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 280, 1000, 160, 160);
                break;
            case 3:
                batch.draw(buttonTexture, 500, 1000);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 500, 1000, 160, 160);
                break;
            case 4:
                batch.draw(buttonTexture, 60, 780);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 60, 780, 160, 160);
                break;
            case 5:
                batch.draw(buttonTexture, 280, 780);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 280, 780, 160, 160);
                break;
            case 6:
                batch.draw(buttonTexture, 500, 780);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 500, 780, 160, 160);
                break;
            case 7:
                batch.draw(buttonTexture, 60, 560);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 60, 560, 160, 160);
                break;
            case 8:
                batch.draw(buttonTexture, 280, 560);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 280, 560, 160, 160);
                break;
            case 9:
                batch.draw(buttonTexture, 500, 560);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 500, 560, 160, 160);
                break;
            case 10:
                batch.draw(buttonTexture, 60, 340);
                if (frameCounter++ == ShipObject.framesArray.length * frameMultiplier - 1) frameCounter = 0;
                batch.draw(ShipObject.framesArray[frameCounter / frameMultiplier], 60, 340, 160, 160);
                break;

        }
    }
}



