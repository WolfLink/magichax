package me.nathanp.magiccreatures.model;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = Creature.class, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CreatureDatabase extends RoomDatabase {

    private static CreatureDatabase instance;

    public abstract CreatureDao creatureDao();

    public static synchronized CreatureDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CreatureDatabase.class, "creature_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
