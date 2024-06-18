package ru.samsung.gamestudio;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class AnimatedSprite extends Sprite {
    private Texture[] frames;
    private int currentFrame;
    private float fps;
    private float timer;

    public AnimatedSprite(Texture[] frames, float framePerSecond) {
        super(frames[0]);
        this.frames = frames;
        fps = framePerSecond;
        currentFrame = 0;
        timer = 0;
    }
    public void update(float elapsedTime) {
        if (timer < 1.0 / fps)
            timer += elapsedTime;
        else {
            timer -= (float) (1.0 / fps);
            nextFrame();
        }
    }
    public void nextFrame() {
        if (currentFrame < frames.length - 1) {
            currentFrame++;
        } else {
            currentFrame = 0;
        }
        setTexture(frames[currentFrame]);
    }
}
