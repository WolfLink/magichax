package me.nathanp.magiccreatures.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CreatureRemoteDao {

    private static final String TAG = "CreatureRemoteDao";
    CreatureRemoteDatabase remoteDatabase;
    LiveData<List<Creature>> creaturesLiveData;

    public CreatureRemoteDao(CreatureRemoteDatabase creatureRemoteDatabase) {
        remoteDatabase = creatureRemoteDatabase;
        creaturesLiveData = remoteDatabase.getCreatures();
    }

    void add(Creature creature) {
        remoteDatabase._add(creature);
    }

    void insert(String which, Creature creature) {
        remoteDatabase._insert(which, creature);
    }

    void update(String which, Creature creature) {
        remoteDatabase._update(which, creature);
    }

    void delete(String which) {
        remoteDatabase._delete(which);
    }

    LiveData<List<Creature>> getCreatures() {
        return creaturesLiveData;
    }
}
