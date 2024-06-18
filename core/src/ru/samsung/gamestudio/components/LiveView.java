package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ru.samsung.gamestudio.GameResources;

public class LiveView extends View {

    private final static int livePadding = 6;

    private Texture texture;

    private int leftLives;

    public LiveView(float x, float y) {
        super(x, y);
        texture = new Texture(GameResources.LIVE_IMG_PATH);
        this.width = texture.getWidth()*2;
        this.height = texture.getHeight()*2;
        leftLives = 0;
    }

    public void setLeftLives(int leftLives) {
        this.leftLives = leftLives;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (leftLives > 0) batch.draw(texture, x + (texture.getWidth()*2 + livePadding)-texture.getWidth(), y-20, width, height);
        if (leftLives > 1) batch.draw(texture, x-texture.getWidth(), y-20, width, height);
        if (leftLives > 2) batch.draw(texture, x + 2 * (texture.getWidth()*2 + livePadding)-texture.getWidth(), y-20, width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

}