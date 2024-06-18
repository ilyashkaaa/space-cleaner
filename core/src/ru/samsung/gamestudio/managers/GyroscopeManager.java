package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class GyroscopeManager {
    boolean gyroscopeAvail;
    float gyroX;
    float gyroY;

    public GyroscopeManager() {
        gyroscopeAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Gyroscope);
        updateGyroscopeFlag();
        gyroX = 0;
        gyroY = 0;
    }

    public void gyroTest() {
            gyroX = Gdx.input.getGyroscopeX();
            gyroY = Gdx.input.getGyroscopeY();
    }

    public float getGyroX() {
        return gyroX;
    }

    public float getGyroY() {
        return gyroY;
    }
    public boolean isGyroscopeAvail() {
        return gyroscopeAvail;
    }
    public void updateGyroscopeFlag() {
        gyroscopeAvail = MemoryManager.loadIsGyroscopeOn();
    }
}
