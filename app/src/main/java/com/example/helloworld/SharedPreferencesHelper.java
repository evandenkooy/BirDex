package com.example.helloworld;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {
    private static final String PREF_NAME = "MyPrefs";
    private static final String SKIN_KEY = "skin";
    private static final String EYES_KEY = "eyes";
    private static final String NOSE_KEY = "nose";
    private static final String MOUTH_KEY = "mouth";
    private static final String HAT_HEY = "hat";
    private static final String BODY_KEY = "body";

    private SharedPreferences sharedPreferences;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveSkin(int value) {
        sharedPreferences.edit().putInt(SKIN_KEY, value).apply();
    }

    public int loadSkin() {
        return sharedPreferences.getInt(SKIN_KEY, R.drawable.skin1);
    }
    public void saveEyes(int value) {
        sharedPreferences.edit().putInt(EYES_KEY, value).apply();
    }

    public int loadEyes() {
        return sharedPreferences.getInt(EYES_KEY, R.drawable.eyes1);
    }

    public void saveNose(int value) {
        sharedPreferences.edit().putInt(NOSE_KEY, value).apply();
    }

    public int loadNose() {
        return sharedPreferences.getInt(NOSE_KEY, R.drawable.nose1);
    }

    public void saveMouth(int value) {
        sharedPreferences.edit().putInt(MOUTH_KEY, value).apply();
    }

    public int loadMouth() {
        return sharedPreferences.getInt(MOUTH_KEY, R.drawable.mouth1);
    }

    public void saveHat(int value) {
        sharedPreferences.edit().putInt(HAT_HEY, value).apply();
    }

    public int loadHat() {
        return sharedPreferences.getInt(HAT_HEY, R.drawable.hat1);
    }

    public void saveBody(int value) {
        sharedPreferences.edit().putInt(BODY_KEY, value).apply();
    }

    public int loadBody() {
        return sharedPreferences.getInt(BODY_KEY, R.drawable.body1);
    }




    // Add similar methods for your other variables
}
