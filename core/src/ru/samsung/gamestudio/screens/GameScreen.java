package ru.samsung.gamestudio.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import ru.samsung.gamestudio.*;
import ru.samsung.gamestudio.components.*;
import ru.samsung.gamestudio.managers.ContactManager;
import ru.samsung.gamestudio.managers.DoubleClickManager;
import ru.samsung.gamestudio.managers.GyroscopeManager;
import ru.samsung.gamestudio.managers.MemoryManager;
import ru.samsung.gamestudio.objects.BossObject;
import ru.samsung.gamestudio.objects.BulletObject;
import ru.samsung.gamestudio.objects.EnemyBulletObject;
import ru.samsung.gamestudio.objects.MeteorObject;
import ru.samsung.gamestudio.objects.ShipObject;
import ru.samsung.gamestudio.objects.TrashObject;
import ru.samsung.gamestudio.objects.UfoObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameScreen extends ScreenAdapter {

    MyGdxGame myGdxGame;
    GameSession gameSession;
    ShipObject shipObject;
    GyroscopeManager gyroscope;

    ArrayList<TrashObject> trashArray;
    ArrayList<MeteorObject> meteorArray;
    ArrayList<UfoObject> ufoArray;
    ArrayList<BossObject> bossArray;
    ArrayList<EnemyBulletObject> enemyBulletArray;
    ArrayList<BulletObject> bulletArray;

    ContactManager contactManager;

    // PLAY state UI
    MovingBackgroundView backgroundView;
    ImageView topBlackoutView;

    LiveView liveView;
    UltraView ultraView;
    ShieldView shieldView;
    Texture shield;
    TextView shieldTimer;
    TextView scoreTextView;
    TextView bossTextView;
    ButtonView pauseButton;
    DoubleClickManager doubleClickManager;

    // PAUSED state UI
    ImageView fullBlackoutView;
    TextView pauseTextView;
    ButtonView homeButton;
    ButtonView continueButton;

    // ENDED state UI
    TextView recordsTextView;
    TextView ultraTextView;

    RecordsListView recordsListView;
    ButtonView homeButton2;
    ButtonView shieldButton;
    private boolean ultraShoot;
    String[] framesArray;
    int frameCounter;
    int frameMultiplier = 10;
    AnimatedSprite amogus;

    Random random;


    public GameScreen(MyGdxGame myGdxGame) {
        this.myGdxGame = myGdxGame;
        gameSession = new GameSession();

        contactManager = new ContactManager(myGdxGame.world);
        doubleClickManager = new DoubleClickManager();

        ultraShoot = false;
        gyroscope = myGdxGame.gyroscope;
        random = new Random();

        trashArray = new ArrayList<>();
        bulletArray = new ArrayList<>();
        meteorArray = new ArrayList<>();
        ufoArray = new ArrayList<>();
        bossArray = new ArrayList<>();
        enemyBulletArray = new ArrayList<>();
        framesArray = new String[]{
                "textures/ship1.png",
                "textures/ship2.png",
                "textures/ship3.png",
                "textures/ship4.png",
                "textures/ship5.png",
        };
        frameCounter = 0;
        shieldView = new ShieldView(566, 45);
        shield = new Texture(GameResources.SHIELD_PATH);
        amogus = new AnimatedSprite(GameResources.AMOGUS, 15);

        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                framesArray[frameCounter / frameMultiplier],
                myGdxGame.world
        );

        backgroundView = new MovingBackgroundView(GameResources.BACKGROUND_IMG_PATH);
        topBlackoutView = new ImageView(0, 1180, GameResources.BLACKOUT_TOP_IMG_PATH);
        ultraView = new UltraView(500, 1100);
        ultraTextView = new TextView(myGdxGame.commonWhiteFont, 420, 1160, "Double Tap to activate!");
        liveView = new LiveView(305, 1215);
        scoreTextView = new TextView(myGdxGame.middleWhiteFont, 50, 1220);
        bossTextView = new TextView(myGdxGame.middleWhiteFont, 50, 1115);
        pauseButton = new ButtonView(
                605, 1200,
                46, 54,
                GameResources.PAUSE_IMG_PATH
        );
        shieldButton = new ButtonView(
                560, 40, 130, 150, GameResources.SHIELD_BUTTON_PATH
        );

        fullBlackoutView = new ImageView(0, 0, GameResources.BLACKOUT_FULL_IMG_PATH);
        pauseTextView = new TextView(myGdxGame.largeWhiteFont, 282, 842, "Pause");
        homeButton = new ButtonView(
                138, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );
        continueButton = new ButtonView(
                393, 695,
                200, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Continue"
        );

        recordsListView = new RecordsListView(myGdxGame.commonWhiteFont, 900);
        recordsTextView = new TextView(myGdxGame.largeWhiteFont, 206, 1052, "Last records");
        homeButton2 = new ButtonView(
                280, 575,
                160, 70,
                myGdxGame.commonBlackFont,
                GameResources.BUTTON_SHORT_BG_IMG_PATH,
                "Home"
        );

    }

    @Override
    public void show() {
        restartGame();
    }

    @Override
    public void render(float delta) {


        handleInput();

        if (gameSession.state == GameState.PLAYING) {
            if (ShieldView.shieldActive && TimeUtils.millis() - ShieldView.timer >= 5000) {
                ShieldView.timer = TimeUtils.millis();
                ShieldView.shieldActive = false;
            }
            if (!gameSession.shouldSpawnBoss()) {
                if (gameSession.shouldSpawnEnemy()) {
                    int enemy = random.nextInt(3);
                    switch (enemy) {
                        case 0:

                            TrashObject trashObject = new TrashObject(
                                    GameSettings.TRASH_WIDTH, GameSettings.TRASH_HEIGHT,
                                    GameResources.TRASH_IMG_PATH,
                                    myGdxGame.world);

                            trashArray.add(trashObject);

                            break;
                        case 1:
                            MeteorObject meteorObject = new MeteorObject(
                                    GameSettings.METEOR_WIDTH, GameSettings.METEOR_HEIGHT,
                                    GameResources.METEOR_IMG_PATH,
                                    myGdxGame.world
                            );
                            meteorArray.add(meteorObject);
                            break;
                        case 2:
                            UfoObject ufoObject = new UfoObject(
                                    GameSettings.UFO_WIDTH, GameSettings.UFO_HEIGHT,
                                    GameResources.UFO_IMG_PATH,
                                    myGdxGame.world
                            );
                            ufoArray.add(ufoObject);
                            break;
                    }
                }
            } else {
                if (ufoArray.size() + trashArray.size() + meteorArray.size() == 0 && bossArray.isEmpty()) {
                    BossObject boss = new BossObject(
                            GameSettings.BOSS_WIDTH, GameSettings.BOSS_HEIGHT,
                            GameResources.BOSS_IMG_PATH,
                            myGdxGame.world);
                    bossArray.add(boss);

                }

            }

            if (shipObject.needToShoot()) {

                BulletObject laserBullet = new BulletObject(
                        shipObject.getX(), shipObject.getY() + shipObject.height / 2,
                        GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                        GameResources.BULLET_IMG_PATH,
                        myGdxGame.world
                );
                bulletArray.add(laserBullet);

                if (ultraShoot && GameSession.ultraCount >= 15) {
                    BulletObject laserBulletLeft = new BulletObject(
                            shipObject.getX() - 30, shipObject.getY() + shipObject.height / 2,
                            GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                            GameResources.BULLET_IMG_PATH,
                            myGdxGame.world
                    );
                    bulletArray.add(laserBulletLeft);
                    BulletObject laserBulletRight = new BulletObject(
                            shipObject.getX() + 30, shipObject.getY() + shipObject.height / 2,
                            GameSettings.BULLET_WIDTH, GameSettings.BULLET_HEIGHT,
                            GameResources.BULLET_IMG_PATH,
                            myGdxGame.world
                    );
                    bulletArray.add(laserBulletRight);
                    GameSession.ultraCount -= 15;
                } else {
                    ultraShoot = false;
                }

                if (myGdxGame.audioManager.isSoundOn) myGdxGame.audioManager.shootSound.play();
            }

            if (!shipObject.isAlive()) {
                gameSession.endGame();
                if (GameSession.difficulty == GameDifficulty.EASY) {
                    recordsListView.setRecords(MemoryManager.loadEasyRecordsTable());
                }
                if (GameSession.difficulty == GameDifficulty.MEDIUM) {
                    recordsListView.setRecords(MemoryManager.loadMediumRecordsTable());
                }
                if (GameSession.difficulty == GameDifficulty.HARD) {
                    recordsListView.setRecords(MemoryManager.loadHardRecordsTable());
                }
            }

            updateTrash();
            updateBullets();
            updateEnemyBullets();
            updateMeteor();
            updateUfo();
            updateBoss();
            backgroundView.move();
            gameSession.updateScore();
            scoreTextView.setText("Score: " + gameSession.getScore());
            liveView.setLeftLives(shipObject.getLiveLeft());
            gyroscope.gyroTest();
            myGdxGame.stepWorld();
        }
        draw();
    }

    private void handleInput() {
        if (Gdx.input.isTouched()) {
            myGdxGame.touch = myGdxGame.camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (!gyroscope.isGyroscopeAvail()) {
                shipObject.move(myGdxGame.touch);
            }
            if (doubleClickManager.doubleTap() && gameSession.difficulty != GameDifficulty.HARD) {
                ultraShoot = true;
            }

            switch (gameSession.state) {
                case PLAYING:
                    if (pauseButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.pauseGame();
                    }
                    if (shieldButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y) && shieldView.isShieldAvailable() && gameSession.difficulty != GameDifficulty.HARD) {
                        shieldView.activate();
                    }
                    break;


                case PAUSED:
                    if (continueButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        gameSession.resumeGame();
                    }
                    if (homeButton.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;

                case ENDED:

                    if (homeButton2.isHit(myGdxGame.touch.x, myGdxGame.touch.y)) {
                        myGdxGame.setScreen(myGdxGame.menuScreen);
                    }
                    break;
            }

        }
    }

    private void draw() {

        myGdxGame.camera.update();
        myGdxGame.batch.setProjectionMatrix(myGdxGame.camera.combined);
        ScreenUtils.clear(Color.CLEAR);

        myGdxGame.batch.begin();
        backgroundView.draw(myGdxGame.batch);
        for (TrashObject trash : trashArray) {
            trash.draw(myGdxGame.batch);
            if (gameSession.state == GameState.PLAYING) {
                trash.rotation(1);
            }
        }
        for (MeteorObject meteor : meteorArray) {
            meteor.draw(myGdxGame.batch);
            if (gameSession.state == GameState.PLAYING) {
                meteor.rotation(2);
            }
        }
        for (UfoObject ufo : ufoArray) ufo.draw(myGdxGame.batch);
        for (BossObject bossObject : bossArray) {
            bossObject.draw(myGdxGame.batch);
            if (gameSession.state == GameState.PLAYING) bossObject.moveBoss();
        }
        for (EnemyBulletObject enemyBullet : enemyBulletArray) enemyBullet.draw(myGdxGame.batch);

        if (gameSession.state == GameState.PLAYING && ShieldView.shieldActive) {
            shieldTimer = new TextView(myGdxGame.largeWhiteFont, 603, 110, "" + (5 - (TimeUtils.millis() - ShieldView.timer) / 1000));
        }
        if (15 - (TimeUtils.millis() - ShieldView.timer) / 1000 > 0 && gameSession.state == GameState.PLAYING && !ShieldView.shieldActive) {
            shieldTimer = new TextView(myGdxGame.largeWhiteFont, 603, 110, "" + (15 - (TimeUtils.millis() - ShieldView.timer) / 1000));
        } else {
            shieldTimer = new TextView(myGdxGame.largeWhiteFont, 603, 110, "");
        }
        if (GameSession.difficulty != GameDifficulty.HARD) {
            shieldButton.draw(myGdxGame.batch);
            shieldView.draw(myGdxGame.batch);
            shieldTimer.draw(myGdxGame.batch);
            ultraView.draw(myGdxGame.batch);
            if (GameSession.ultraCount >= 15) {
                ultraTextView.draw(myGdxGame.batch);
            }
        }
        if (gameSession.state == GameState.PLAYING) {
            shipObject.draw(myGdxGame.batch);
        } else {
            shipObject.pauseDraw(myGdxGame.batch);
        }
        for (BulletObject bullet : bulletArray) bullet.draw(myGdxGame.batch);
        topBlackoutView.draw(myGdxGame.batch);
        scoreTextView.draw(myGdxGame.batch);
        bossTextView.draw(myGdxGame.batch);
        liveView.draw(myGdxGame.batch);
        pauseButton.draw(myGdxGame.batch);
        if (ShieldView.shieldActive) {
            myGdxGame.batch.draw(shield, shipObject.getX() - 100, shipObject.getY() - 100, 200, 200);
        }

        if (gyroscope.isGyroscopeAvail()) {
            shipObject.move(gyroscope.getGyroX(), gyroscope.getGyroY());
        }
        if (gameSession.state == GameState.PAUSED) {
            fullBlackoutView.draw(myGdxGame.batch);
            pauseTextView.draw(myGdxGame.batch);
            homeButton.draw(myGdxGame.batch);
            continueButton.draw(myGdxGame.batch);
        } else if (gameSession.state == GameState.ENDED) {
            fullBlackoutView.draw(myGdxGame.batch);
            recordsTextView.draw(myGdxGame.batch);
            recordsListView.draw(myGdxGame.batch);
            homeButton2.draw(myGdxGame.batch);
            amogus.update(Gdx.graphics.getDeltaTime());
            amogus.setPosition(110, 100);
            amogus.draw(myGdxGame.batch);
        }

        myGdxGame.batch.end();

    }

    private void updateTrash() {
        Iterator<TrashObject> iterator = trashArray.iterator();
        while (iterator.hasNext()) {
            TrashObject trashObject = iterator.next();

            boolean hasToBeDestroyed = !trashObject.isAlive() || !trashObject.isInFrame();

            if (!trashObject.isAlive()) {
                gameSession.killRegistration();
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(trashObject.body);
                iterator.remove();
            }
        }
    }

    private void updateMeteor() {
        Iterator<MeteorObject> iterator = meteorArray.iterator();
        while (iterator.hasNext()) {
            MeteorObject meteorObject = iterator.next();

            boolean hasToBeDestroyed = !meteorObject.isAlive() || !meteorObject.isInFrame();
            meteorObject.damaged();

            if (!meteorObject.isAlive()) {
                gameSession.killRegistration();
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(meteorObject.body);
                iterator.remove();
            }

        }
    }

    private void updateUfo() {
        Iterator<UfoObject> iterator = ufoArray.iterator();
        while (iterator.hasNext()) {
            UfoObject ufoObject = iterator.next();

            boolean hasToBeDestroyed = !ufoObject.isAlive() || !ufoObject.isInFrame();

            if (!ufoObject.isAlive()) {
                gameSession.killRegistration();
                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

            if (hasToBeDestroyed) {
                myGdxGame.world.destroyBody(ufoObject.body);
                iterator.remove();
            }

        }
    }

    private void updateBoss() {
        if (!gameSession.shouldSpawnBoss()) {
            bossTextView.setText("Kills left for Boss: " + gameSession.killsLeftBoss());
        }
        Iterator<BossObject> iterator = bossArray.iterator();

        while (iterator.hasNext()) {
            BossObject bossObject = iterator.next();
            if (bossObject.needToShoot()) {
                EnemyBulletObject bossBullet = new EnemyBulletObject(
                        bossObject.getX(), bossObject.getY() - (float) bossObject.height / 2 - 10,
                        GameSettings.ENEMY_BULLET_WIDTH, GameSettings.ENEMY_BULLET_HEIGHT,
                        GameResources.ENEMY_BULLET_IMG_PATH,
                        myGdxGame.world);
                enemyBulletArray.add(bossBullet);


            }
            if (gameSession.shouldSpawnBoss()) {
                bossTextView.setText("HP left:" + bossObject.getLivesLeft());
            }
            if (!bossObject.isAlive()) {
                gameSession.killRegistration();
                gameSession.killedBoss();
                myGdxGame.world.destroyBody(bossObject.body);
                iterator.remove();

                if (myGdxGame.audioManager.isSoundOn)
                    myGdxGame.audioManager.explosionSound.play(0.2f);
            }

        }
    }

    private void updateBullets() {
        Iterator<BulletObject> iterator = bulletArray.iterator();
        while (iterator.hasNext()) {
            BulletObject bullet = iterator.next();
            if (bullet.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bullet.body);
                iterator.remove();
            }
        }
    }

    private void updateEnemyBullets() {
        Iterator<EnemyBulletObject> iterator = enemyBulletArray.iterator();
        while (iterator.hasNext()) {
            EnemyBulletObject bullet = iterator.next();
            if (bullet.hasToBeDestroyed()) {
                myGdxGame.world.destroyBody(bullet.body);
                iterator.remove();
            }
        }
    }


    private void restartGame() {
        Iterator<BossObject> iteratorBoss = bossArray.iterator();
        while (iteratorBoss.hasNext()) {
            BossObject boss = iteratorBoss.next();
            myGdxGame.world.destroyBody(boss.body);
            iteratorBoss.remove();
        }

        Iterator<UfoObject> iteratorUfo = ufoArray.iterator();
        while (iteratorUfo.hasNext()) {
            UfoObject ufo = iteratorUfo.next();
            myGdxGame.world.destroyBody(ufo.body);
            iteratorUfo.remove();
        }

        Iterator<TrashObject> iteratorTrash = trashArray.iterator();
        while (iteratorTrash.hasNext()) {
            TrashObject trash = iteratorTrash.next();
            myGdxGame.world.destroyBody(trash.body);
            iteratorTrash.remove();
        }
        Iterator<MeteorObject> iteratorMeteor = meteorArray.iterator();
        while (iteratorMeteor.hasNext()) {
            MeteorObject meteor = iteratorMeteor.next();
            myGdxGame.world.destroyBody(meteor.body);
            iteratorMeteor.remove();
        }

        if (shipObject != null) {
            myGdxGame.world.destroyBody(shipObject.body);
        }
        shipObject = new ShipObject(
                GameSettings.SCREEN_WIDTH / 2, 150,
                GameSettings.SHIP_WIDTH, GameSettings.SHIP_HEIGHT,
                GameResources.SHIP_IMG_PATH,
                myGdxGame.world
        );
        ShieldView.timer = TimeUtils.millis();
        bulletArray.clear();
        gameSession.startGame();
    }

}