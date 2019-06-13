package me.nathanp.magiccreatures.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TraitRepository {
    private static final String TAG = "TraitRepository";
    private TraitRemoteDao remoteDao;

    public TraitRepository() {
        remoteDao = new TraitRemoteDao();
    }

    public LiveData<List<Trait>> getAllTraits() {
        return remoteDao.getAllTraits();
    }
}
