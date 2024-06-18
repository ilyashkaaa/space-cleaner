package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import ru.samsung.gamestudio.GameSettings;

public class EnemyBulletObject extends GameObject {
    boolean wasHit;
    public EnemyBulletObject(float x, float y, int width, int height, String texturePath, World world) {
        super(texturePath, (int) x, (int) y, width, height, GameSettings.ENEMY_BULLET_BIT, world);
        body.setLinearVelocity(new Vector2(0, (float) -GameSettings.BULLET_VELOCITY /5));
        wasHit = false;
    }
    public boolean hasToBeDestroyed() {
        return wasHit || (getY() - (float) height / 2 > GameSettings.SCREEN_HEIGHT);
    }

    @Override
    public void hit() {
        wasHit = true;
    }
}
