package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;
import ru.samsung.gamestudio.GameSettings;
import ru.samsung.gamestudio.components.ShieldView;

public class ShipObject extends GameObject {

    long lastShotTime;
    int livesLeft;
    int frameCounter;
    int frameMultiplier = 6;
    public static Texture[] framesArray = new Texture[]{
        new Texture("textures/ships/ship1/0.png"),
                new Texture("textures/ships/ship1/1.png"),
                new Texture("textures/ships/ship1/2.png"),
                new Texture("textures/ships/ship1/3.png"),

    };
    public ShipObject(int x, int y, int width, int height, String texturePath, World world) {
        super(texturePath, x, y, width, height, GameSettings.SHIP_BIT, world);
        body.setLinearDamping(10);
        livesLeft = 3;
        frameCounter = 0;
    }


    public int getLiveLeft() {
        return livesLeft;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (frameCounter++ == framesArray.length * frameMultiplier - 1) frameCounter = 0;
        putInFrame();
        batch.draw(framesArray[frameCounter / frameMultiplier],  getX()-width/2,  getY()-height/2, width, height);
    }
    public void pauseDraw(SpriteBatch batch) {
        putInFrame();
        batch.draw(framesArray[frameCounter / frameMultiplier],  getX()-width/2,  getY()-height/2, width, height);
    }

    public void move(float gyroX, float gyroY) {
        body.applyForceToCenter(new Vector2(
                        (gyroY) * GameSettings.SHIP_FORCE_RATIO*300,
                        -(gyroX) * GameSettings.SHIP_FORCE_RATIO*300),
                true
        );
    }
    public void move(Vector3 vector3) {
        body.applyForceToCenter(new Vector2(
                        (float) ((vector3.x - getX()) * GameSettings.SHIP_FORCE_RATIO*1.5),
                        (float) ((vector3.y - getY()) * GameSettings.SHIP_FORCE_RATIO*1.5)),
                true
        );
    }

    private void putInFrame() {
        if (getY() > (GameSettings.SCREEN_HEIGHT / 2f - height / 2f)) {
            setY((int) (GameSettings.SCREEN_HEIGHT / 2f - height / 2f));
        }
        if (getY() <= (height / 2f)) {
            setY(height / 2);
        }
        if (getX() < (-width / 2f)) {
            setX(GameSettings.SCREEN_WIDTH);
        }
        if (getX() > (GameSettings.SCREEN_WIDTH + width / 2f)) {
            setX(0);
        }
    }

    public boolean needToShoot() {
        if (TimeUtils.millis() - lastShotTime >= GameSettings.SHOOTING_COOL_DOWN) {
            lastShotTime = TimeUtils.millis();
            return true;
        }
        return false;
    }

    @Override
    public void hit() {
        if (!ShieldView.shieldActive) {
            livesLeft -= 1;
        }
    }
    @Override
    public void bump() {
        if (!ShieldView.shieldActive) {
            livesLeft -= 1;
        }
    }

    public boolean isAlive() {
        return livesLeft > 0;
    }
}
