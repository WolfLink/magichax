package me.nathanp.magiccreatures.model;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CreatureDao {

    @Insert
    void insert(Creature creature);

    @Update
    void update(Creature creature);

    @Delete
    void delete(Creature creature);

    @Query("SELECT * FROM creature_cache_table ORDER BY active")
    LiveData<List<Creature>> getAllCreatures();

}
