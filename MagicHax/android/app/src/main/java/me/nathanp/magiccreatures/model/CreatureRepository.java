package me.nathanp.magiccreatures.model;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CreatureRepository {
    private static final String LOG_TAG = "CreatureRepository";
    private CreatureRemoteDao creatureRemoteDao;
    private LiveData<List<Creature>> allCreatures;

    public CreatureRepository(Application application) {
        // Not using for now
//        CreatureDatabase creatureDatabase = CreatureDatabase.getInstance(application);
//        CreatureDao creatureDao = creatureDatabase.creatureDao();
        CreatureRemoteDatabase creatureRemoteDatabase = CreatureRemoteDatabase.getInstance();
        creatureRemoteDao = creatureRemoteDatabase.creatureRemoteDao();
        allCreatures = creatureRemoteDao.getCreatures();
    }

    public void add(final Creature creature) {
        new CreatureAsyncTask(new Operation() {
            @Override
            public void operation() {
                creatureRemoteDao.add(creature);
            }
        });
    }

    public void insert(final String which, final Creature creature) {
        new CreatureAsyncTask(new Operation() {
            @Override
            public void operation() {
                creatureRemoteDao.insert(which, creature);
//                creatureDao.insert(creature);
            }
        }).execute();
    }

    public void update(final String which, final Creature creature) {
        new CreatureAsyncTask(new Operation() {
            @Override
            public void operation() {
                creatureRemoteDao.update(which, creature);
//                creatureDao.update(creature);
            }
        }).execute();
    }

    public void delete(final String which) {
        new CreatureAsyncTask(new Operation() {
            @Override
            public void operation() {
                creatureRemoteDao.delete(which);
//                creatureDao.delete(creature);
            }
        }).execute();
    }

    public LiveData<List<Creature>> getAllCreatures() {
        return allCreatures;
    }

    private static class CreatureAsyncTask extends AsyncTask<Void, Void, Void> {
        private Operation creatureOperation;
        CreatureAsyncTask(Operation creatureOperation) {
            this.creatureOperation = creatureOperation;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            creatureOperation.operation();
            return null;
        }
    }

    private interface Operation {
        void operation();
    }
}
