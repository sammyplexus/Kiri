package com.agbede.kiri.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.agbede.kiri.models.Results;

@Database(entities = {Results.class}, version = 1, exportSchema = false)
public abstract class MovieDb extends RoomDatabase {
    private static String DATABASE_NAME = "MOVIE_DATABASE";
    private static MovieDb db;
    public abstract MovieDao getDao();

    public static MovieDb getDb(Context context){
        if (db == null){
            db = Room.databaseBuilder(context.getApplicationContext(), MovieDb.class, DATABASE_NAME).build();
        }
        return db;
    }

    public static void destroyInstance(){
        db = null;
    }
}
