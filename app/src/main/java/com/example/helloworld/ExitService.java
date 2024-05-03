package com.example.helloworld;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class ExitService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        Log.d("ExitService", "DONED");
        clearSharedPreferences(); // Clear SharedPreferences when the app is closed
        stopSelf(); // Stop the service
    }
    private void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("SelectedNumbers", Context.MODE_PRIVATE);
        Log.d("ExitService", preferences.toString());
        preferences.edit().clear().apply();
    }


}
