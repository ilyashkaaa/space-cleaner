package ru.samsung.gamestudio.managers;

public class DoubleClickManager {

    private static final float DOUBLE_TAP_MAX_TIME = 400; // Максимальное время между двойными нажатиями
    private static final float DOUBLE_TAP_MIN_TIME = 40; // Мин время между двойными нажатиями
    private long lastTapTime;


    public boolean doubleTap() {
        if (getLastTapTime()) {
            return true;
        }
        lastTapTime = System.currentTimeMillis();
        return false;
    }

    private boolean getLastTapTime() {
        return (System.currentTimeMillis() - lastTapTime < DOUBLE_TAP_MAX_TIME && System.currentTimeMillis() - lastTapTime > DOUBLE_TAP_MIN_TIME);
    }

}

