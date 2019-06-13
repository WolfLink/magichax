package me.nathanp.magiccreatures.viewmodel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.CreatureRepository;

public class CreatureListViewModel extends AndroidViewModel {
    private static final String TAG = "CreatureListViewModel";
    private CreatureRepository creatureRepository;

    public CreatureListViewModel(@NonNull Application application) {
        super(application);
        creatureRepository = new CreatureRepository();
    }

    public void add(Creature creature) {
        creatureRepository.addCreature(creature);
    }

    public void update(String which, Creature creature) {
        creatureRepository.addCreature(creature);
    }

    public void delete(String which) {
        creatureRepository.deleteCreature(which);
    }

    public LiveData<List<Creature>> getAllCreatures() {
        return creatureRepository.getAllCreatures();
    }
}
