package me.nathanp.magiccreatures.viewmodel;

import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.CreatureRepository;

public class CreatureViewModel extends AndroidViewModel {
    private static final String LOG_TAG = "CreatureViewModel";
    private CreatureRepository creatureRepository;
    private LiveData<List<Creature>> allCreatures;

    public CreatureViewModel(@NonNull Application application) {
        super(application);
        creatureRepository = new CreatureRepository(application);
        allCreatures = creatureRepository.getAllCreatures();
    }

    public void insert(String which, Creature creature) {
        creatureRepository.insert(which, creature);
    }

    public void update(String which, Creature creature) {
        creatureRepository.update(which, creature);
    }

    public void delete(String which) {
        creatureRepository.delete(which);
    }

    public LiveData<List<Creature>> getAllCreatures() {
        return allCreatures;
    }
}
