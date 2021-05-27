package com.example.rubrica.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.rubrica.bean.Contatto;
import com.example.rubrica.dao.ContattoDAO;

@Database(entities = {Contatto.class}, version = 1, exportSchema = false)
public abstract class DBManager extends RoomDatabase {
    private static final String DB_NOME = "Rubrica";
    private static DBManager instance;

    public static DBManager getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DBManager.class, DB_NOME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract ContattoDAO getContattoDAO();
}
