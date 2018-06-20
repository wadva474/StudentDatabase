package com.example.musa.studentdatabase.DATABASE;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;




@Database(entities = {StudentDetails.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase sInstance;
    private  static final String  DataBaseName="Students";
    private static final Object LOCK =new Object();



    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.e("error creating", "getsInstance: Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, AppDatabase.DataBaseName)
                        .build();
            }

        }
        return sInstance;
    }


     abstract public   StudentDao studentDao();

}
