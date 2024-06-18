package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;

public class UltraView extends View{
    private Texture textureBar;
    private Texture textureLevel;
    public UltraView(float x, float y) {
        super(x, y);
        textureBar = new Texture(GameResources.ULTRA_BAR_IMG_PATH);
        textureLevel = new Texture(GameResources.ULTRA_LEVEL_IMG_PATH);
        this.width = textureBar.getWidth();
        this.height = textureBar.getHeight();

    }
    @Override
    public void draw(SpriteBatch batch) {

        if (GameSession.ultraCount<100) {
            batch.draw(textureLevel, x, y, (float) (GameSession.ultraCount*1.95), height);
        } else {
            batch.draw(textureLevel, x, y, width, height);
        }
        batch.draw(textureBar, x, y, width, height);
    }

}
