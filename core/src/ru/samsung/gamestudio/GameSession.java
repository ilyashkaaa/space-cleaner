package ru.samsung.gamestudio;

import com.badlogic.gdx.utils.TimeUtils;

import ru.samsung.gamestudio.components.ShieldView;
import ru.samsung.gamestudio.managers.MemoryManager;

import java.util.ArrayList;
import java.util.Random;


public class GameSession {

    public GameState state;
    public static GameDifficulty difficulty;
    long sessionStartTime;
    long nextEnemySpawnTime;
    long pauseStartTime;
    private int score;
    int kills;
    int nextBossScore;
    public static float ultraCount;
    Random random;

    public GameSession() {
    }

    public void startGame() {

        state = GameState.PLAYING;

        score = 0;
        nextBossScore = 0;
        kills = 0;
        ultraCount = 0;
        random = new Random();
        sessionStartTime = TimeUtils.millis();

    }

    public void pauseGame() {
        state = GameState.PAUSED;
        pauseStartTime = TimeUtils.millis();
    }

    public void resumeGame() {
        state = GameState.PLAYING;
        sessionStartTime += TimeUtils.millis() - pauseStartTime;
        ShieldView.timer += (TimeUtils.millis() - pauseStartTime);
    }

    public void endGame() {
        updateScore();
        state = GameState.ENDED;
        if (difficulty == GameDifficulty.EASY) {
            ArrayList<Integer> recordsTable = MemoryManager.loadEasyRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < getScore()) break;
            }
            recordsTable.add(foundIdx, getScore());
            MemoryManager.saveTableOfEasyRecords(recordsTable);
        }
        if (difficulty == GameDifficulty.MEDIUM) {
            ArrayList<Integer> recordsTable = MemoryManager.loadMediumRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < getScore()) break;
            }
            recordsTable.add(foundIdx, getScore());
            MemoryManager.saveTableOfMediumRecords(recordsTable);
        }
        if (difficulty == GameDifficulty.HARD) {
            ArrayList<Integer> recordsTable = MemoryManager.loadHardRecordsTable();
            if (recordsTable == null) {
                recordsTable = new ArrayList<>();
            }
            int foundIdx = 0;
            for (; foundIdx < recordsTable.size(); foundIdx++) {
                if (recordsTable.get(foundIdx) < getScore()) break;
            }
            recordsTable.add(foundIdx, getScore());
            MemoryManager.saveTableOfHardRecords(recordsTable);
        }


    }

    public void killRegistration() {
        kills += 1;
        nextBossScore += 100;
        if (ultraCount > 100) {
            ultraCount = 100;
        }
        ultraCount += 5;
    }

    public void updateScore() {
        score = (int) (TimeUtils.millis() - sessionStartTime) / 100 + kills * 100;
    }

    public int getScore() {
        return score;
    }


    public boolean shouldSpawnBoss() {
        return nextBossScore > 2000;
    }

    public void killedBoss() {
        nextBossScore = 0;
    }

    public int killsLeftBoss() {
        return (2000 - nextBossScore) / 100;
    }

    public boolean shouldSpawnEnemy() {
        if (nextEnemySpawnTime <= TimeUtils.millis()) {
            nextEnemySpawnTime = TimeUtils.millis() + (long) (GameSettings.STARTING_ENEMY_APPEARANCE_COOL_DOWN
                    * getEnemyPeriodCoolDown());
            return true;
        }
        return false;
    }

    private float getEnemyPeriodCoolDown() {
        if (difficulty != GameDifficulty.EASY) {
            return (float) Math.exp(-0.005 * (TimeUtils.millis() - sessionStartTime + 1) / 1000);
        }
        return 1;
    }
}

