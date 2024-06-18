package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Random;

import ru.samsung.gamestudio.GameSettings;

public class MeteorObject extends GameObject {
    private int livesLeft;
    boolean damaged;
    private static final int paddingHorizontal = 30;

    public MeteorObject(int width, int height, String texturePath, World world) {
        super(texturePath,
                width / 2 + paddingHorizontal + (new Random()).nextInt(GameSettings.SCREEN_WIDTH - 2 * paddingHorizontal - width),
                GameSettings.SCREEN_HEIGHT + height / 2,
                width, height,
                GameSettings.METEOR_BIT,
                world
        );
        texture.rotate(random.nextInt(360));

        body.setLinearVelocity(new Vector2(0, -GameSettings.METEOR_VELOCITY));
        livesLeft = 2;
        damaged = false;
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
        damaged = true;
    }

    @Override
    public void bump() {
        livesLeft -= 1000;
    }

    public void damaged() {
        if (damaged) {
            texture.setTexture(new Texture("textures/meteorDamaged.png"));

        }
    }
}

