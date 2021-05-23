package com.remotestate.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface LocationDao {

    @Insert
    void Insert(LocationTable locationTable);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void Update(LocationTable locationTable);

    @Delete
    void Delete(LocationTable locationTable);

    @Query("DELETE FROM location_table")
    void DeleteAllNotes();

    @Query("SELECT * FROM location_table")
    LiveData<List<LocationTable>> getAllLatLng();  //updates and returns


}
