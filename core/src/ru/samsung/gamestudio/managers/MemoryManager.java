package ru.samsung.gamestudio.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;

public class MemoryManager {

    private static final Preferences preferences = Gdx.app.getPreferences("User saves");

    public static void saveSoundSettings(boolean isOn) {
        preferences.putBoolean("isSoundOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsSoundOn() {
        return preferences.getBoolean("isSoundOn", true);
    }
    public static void saveGyroscopeSettings(boolean isOn) {
        preferences.putBoolean("isGyroscopeOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsGyroscopeOn() {
        return preferences.getBoolean("isGyroscopeOn", true);
    }

    public static void saveMusicSettings(boolean isOn) {
        preferences.putBoolean("isMusicOn", isOn);
        preferences.flush();
    }

    public static boolean loadIsMusicOn() {
        return preferences.getBoolean("isMusicOn", true);
    }

    public static void saveTableOfEasyRecords(ArrayList<Integer> table) {

        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("easyRecordTable", tableInString);
        preferences.flush();
    }

    public static ArrayList<Integer> loadEasyRecordsTable() {
        if (!preferences.contains("easyRecordTable"))
            return null;
        String scores = preferences.getString("easyRecordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }
    public static void saveTableOfMediumRecords(ArrayList<Integer> table) {
        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("mediumRecordTable", tableInString);
        preferences.flush();
    }

    public static ArrayList<Integer> loadMediumRecordsTable() {
        if (!preferences.contains("mediumRecordTable"))
            return null;

        String scores = preferences.getString("mediumRecordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }
    public static void saveTableOfHardRecords(ArrayList<Integer> table) {

        Json json = new Json();
        String tableInString = json.toJson(table);
        preferences.putString("hardRecordTable", tableInString);
        preferences.flush();
    }

    public static ArrayList<Integer> loadHardRecordsTable() {
        if (!preferences.contains("hardRecordTable"))
            return null;
        String scores = preferences.getString("hardRecordTable");
        Json json = new Json();
        ArrayList<Integer> table = json.fromJson(ArrayList.class, scores);
        return table;
    }

}