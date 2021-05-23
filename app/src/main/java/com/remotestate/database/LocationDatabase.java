package com.remotestate.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = LocationTable.class, version = 1, exportSchema = false)
abstract public class LocationDatabase extends RoomDatabase {

    private static LocationDatabase instance; //only one interface

    public abstract LocationDao locationDao();

    public static synchronized LocationDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, "Database_name")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    } //synchronized : only one thread can access this method
    //fallbacktodestructivemigration : to handle versions

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // new PopulateDb(instance).execute();
        }
    };


}
