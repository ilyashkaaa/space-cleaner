package ru.samsung.gamestudio.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;

import java.sql.Time;

import ru.samsung.gamestudio.GameResources;
import ru.samsung.gamestudio.GameSession;

public class ShieldView extends View{
    Texture shieldIcon;
    Texture shieldCD;
   public static long timer;

    public static boolean shieldActive;
    public ShieldView(float x, float y) {
        super(x, y);
        shieldIcon = new Texture(GameResources.SHIELD_ICON_PATH);
        shieldCD = new Texture(GameResources.SHIELD_CD_PATH);
        this.width = shieldIcon.getWidth();
        this.height = shieldIcon.getHeight();

        shieldActive = false;
        timer = TimeUtils.millis();
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(shieldIcon, x, y, width, height);
        if (!isShieldAvailable()) {
            batch.draw(shieldCD, x, y, width, height);
        }
    }
    public boolean isShieldAvailable() {
        return TimeUtils.millis() - timer >= 15000;
    }

    public void activate() {
        shieldActive = true;
        timer = TimeUtils.millis();
    }



}
