package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;


import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameSettings;

public class BossObject extends GameObject {

    int livesLeft;
    long lastShotTime;
    float x, y;
    boolean right;
    boolean spawned;

    public BossObject(int width, int height, String texturePath, World world) {
        super(texturePath,
                360,
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.BOSS_BIT,
                world
        );

        livesLeft = 15;
        spawned = false;
        x = getX();
        y = getY();
    }

    @Override
    public void draw(SpriteBatch batch) {

        batch.draw(texture,
                x - (width / 2f),
                y - (height / 2f),
                width,
                height);
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    @Override
    public void hit() {
        if (spawned) --livesLeft;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN * 2L && spawned) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }
    public void moveBoss() {
        if (y > 1000) {
            y -= 60 * Gdx.graphics.getDeltaTime();
        } else {
            spawned = true;
            y = 1000;
            if (right) {
                x += 160 * Gdx.graphics.getDeltaTime();
            } else {
                x -= 160 * Gdx.graphics.getDeltaTime();
            }
            if (x > 635) {
                right = false;
            }
            if (x < 85) {
                right = true;
            }
        }
        setX(x);
        setY(y);
    }
}
