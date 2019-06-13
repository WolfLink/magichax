package me.nathanp.magiccreatures.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CreatureRepository {
    private static final String TAG = "CreatureRepository";
    private CreatureRemoteDao remoteDao;

    public CreatureRepository() {
        remoteDao = new CreatureRemoteDao();
    }

    public LiveData<List<Creature>> getAllCreatures() {
        return remoteDao.getAllCreatures();
    }

    public String addCreature(Creature creature) {
        return remoteDao.add(creature);
    }

    public void deleteCreature(String id) {
        remoteDao.delete(id);
    }
}
