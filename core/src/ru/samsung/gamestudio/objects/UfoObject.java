package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSettings;


public class UfoObject extends GameObject {
    private static final int paddingHorizontal = 30;
    private int livesLeft;

    public UfoObject(int width, int height, String texturePath, World world) {
        super(texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.UFO_BIT,
                world
        );
        livesLeft = 1;
        body.setLinearVelocity(0, -GameSettings.UFO_VELOCITY);

    }

    public boolean isAlive() {
        return livesLeft > 0;
    }

    public boolean isInFrame() {
        return getY() + height / 2 > 0;
    }

    @Override
    public void hit() {
        livesLeft -= 1;
    }

    @Override
    public float getX() {
        return (float) ((Math.sin((double) getY() / 100) * 280) + 360);
    }

    @Override
    public void draw(SpriteBatch batch) {
        setX(getX());
        batch.draw(texture,
                getX() - (width / 2f),
                getY() - (height / 2f),
                width,
                height);
    }
}
