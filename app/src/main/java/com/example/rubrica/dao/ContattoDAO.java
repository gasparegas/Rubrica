package com.example.rubrica.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.rubrica.bean.Contatto;

import java.util.List;

@Dao
public interface ContattoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertContatto(Contatto contatto);

    @Query("DELETE from Contatto")
    void deleteAll();

    @Query("SELECT * from Contatto WHERE idContatto = :idContatto")
    Contatto getContatto(long idContatto);

    @Query("DELETE from Contatto WHERE idContatto = :idContatto")
    void deleteContatto(long idContatto);

    @Query("SELECT * from Contatto ORDER BY nome")
    List<Contatto> getContatti();

}

