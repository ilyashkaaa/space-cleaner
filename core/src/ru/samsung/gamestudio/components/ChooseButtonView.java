package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ChooseButtonView extends View {
    Texture textureButton;
    Texture textureImage;
    BitmapFont bitmapFont;

    String text;

    float textX;
    float textY;


    public ChooseButtonView(float x, float y, float width, float height, BitmapFont font, String textureButtonPath, String imagePath, String text) {
        super(x, y, width, height);

        this.text = text;
        this.bitmapFont = font;

        textureButton = new Texture(textureButtonPath);
        textureImage = new Texture(imagePath);


        GlyphLayout glyphLayout = new GlyphLayout(bitmapFont, text);
        float textWidth = glyphLayout.width;
        float textHeight = glyphLayout.height;

        textX = x + (width - textWidth) / 2 ;
        textY = y-8;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(textureButton, x, y, width, height);

        batch.draw(textureImage, x, y, width, height);
        if (bitmapFont != null) bitmapFont.draw(batch, text, textX, textY);

    }
}
