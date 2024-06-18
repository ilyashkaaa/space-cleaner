package ru.samsung.gamestudio;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import ru.samsung.gamestudio.managers.AudioManager;
import ru.samsung.gamestudio.managers.GyroscopeManager;
import ru.samsung.gamestudio.screens.ControlScreen;
import ru.samsung.gamestudio.screens.GameScreen;
import ru.samsung.gamestudio.screens.LevelScreen;
import ru.samsung.gamestudio.screens.MenuScreen;
import ru.samsung.gamestudio.screens.MusicScreen;
import ru.samsung.gamestudio.screens.SettingsScreen;
import ru.samsung.gamestudio.screens.StarshipScreen;
import ru.samsung.gamestudio.screens.WeaponScreen;

import static ru.samsung.gamestudio.GameSettings.*;

public class MyGdxGame extends Game {

    public World world;

    public BitmapFont largeWhiteFont;
    public BitmapFont commonWhiteFont;
    public BitmapFont middleWhiteFont;
    public BitmapFont commonBlackFont;
    public BitmapFont plushLargeWhiteFont;
    public BitmapFont plushMiddleWhiteFont;

    public Vector3 touch;
    public SpriteBatch batch;
    public OrthographicCamera camera;
    public AudioManager audioManager;
    public GyroscopeManager gyroscope;

    public GameScreen gameScreen;
    public MenuScreen menuScreen;
    public SettingsScreen settingsScreen;
    public WeaponScreen weaponScreen;
    public ControlScreen controlScreen;
    public  LevelScreen levelScreen;
    public StarshipScreen starshipScreen;
    public MusicScreen musicScreen;

    float accumulator = 0;

    @Override
    public void create() {

        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        largeWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.PLUSH_FONT_PATH);
        commonWhiteFont = FontBuilder.generate(24, Color.WHITE, GameResources.FONT_PATH);
        commonBlackFont = FontBuilder.generate(24, Color.BLACK, GameResources.PLUSH_FONT_PATH);
        middleWhiteFont = FontBuilder.generate(32, Color.WHITE, GameResources.FONT_PATH);
        plushLargeWhiteFont = FontBuilder.generate(64, Color.WHITE, GameResources.PLUSH_FONT_PATH);
        plushMiddleWhiteFont = FontBuilder.generate(48, Color.WHITE, GameResources.PLUSH_FONT_PATH);

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameSettings.SCREEN_WIDTH, GameSettings.SCREEN_HEIGHT);
        audioManager = new AudioManager();
        gyroscope = new GyroscopeManager();

        gameScreen = new GameScreen(this);
        menuScreen = new MenuScreen(this);
        settingsScreen = new SettingsScreen(this);
        levelScreen = new LevelScreen(this);
        weaponScreen = new WeaponScreen(this);
        musicScreen = new MusicScreen(this);
        controlScreen = new ControlScreen(this);
        starshipScreen = new StarshipScreen(this);

        setScreen(menuScreen);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        if (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            world.step(STEP_TIME, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        }
    }
}
