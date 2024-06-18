package ru.samsung.gamestudio.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static ru.samsung.gamestudio.GameSettings.SCALE;

import java.util.Random;

import ru.samsung.gamestudio.GameSession;

public class GameObject {

    public short cBits;

    public int width;
    public int height;

    public Body body;
    Sprite texture;
    boolean left;
    Random random;

    GameObject(String texturePath, int x, int y, int width, int height, short cBits, World world) {
        this.width = width;
        this.height = height;
        this.cBits = cBits;

        texture = new Sprite(new Texture(texturePath));
        body = createBody(x, y, world);
        random = new Random();
        left = random.nextBoolean();


    }

    public void draw(SpriteBatch batch) {
        texture.setPosition(getX() - width / 2, getY() - height / 2);
        texture.draw(batch);
    }

    public void hit() {
        // all physics objects could be hit
    }

    public void bump() {
        // . . .
    }

    public float getX() {
        return (int) (body.getPosition().x / SCALE);
    }

    public float getY() {
        return (body.getPosition().y / SCALE);
    }

    public void setX(float x) {
        body.setTransform(x * SCALE, body.getPosition().y, 0);
    }

    public void setY(float y) {
        body.setTransform(body.getPosition().x, y * SCALE, 0);
    }

    private Body createBody(float x, float y, World world) {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.fixedRotation = true;
        Body body = world.createBody(def);

        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(Math.max(width, height) * SCALE / 2f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = cBits;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        circleShape.dispose();

        body.setTransform(x * SCALE, y * SCALE, 0);
        return body;
    }

    public void rotation(float angle) {
        if (left) {
            texture.rotate(angle);
        } else {
            texture.rotate(-angle);
        }
    }
}
