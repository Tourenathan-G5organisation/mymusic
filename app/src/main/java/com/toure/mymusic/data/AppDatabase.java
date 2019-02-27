package com.toure.mymusic.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;

public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String LOG_TAC = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "mymusic_db";

    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAC, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAC, "Getting the database instance");
        return sInstance;
    }

}
