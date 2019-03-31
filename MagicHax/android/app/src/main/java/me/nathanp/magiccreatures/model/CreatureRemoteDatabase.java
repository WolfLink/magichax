package me.nathanp.magiccreatures.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class CreatureRemoteDatabase {
    private static final String LOG_TAG = "CreatureRemoteDatabase";
    private static CreatureRemoteDatabase instance;
    private static CreatureRemoteDao creatureRemoteDao;

    private final LiveFirebaseData liveFirebaseData;
    private final LiveData<List<Creature>> creaturesLiveData;

    private CreatureRemoteDatabase() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            liveFirebaseData = new LiveFirebaseData(FirebaseDatabase.getInstance().getReference(Paths.getCreaturesPath(user.getUid())));
        }
        else {
            liveFirebaseData = new LiveFirebaseData(FirebaseDatabase.getInstance().getReference(Paths.getCreaturesPath("default")));
        }
        creaturesLiveData = Transformations.map(liveFirebaseData, new Conversions());

        creatureRemoteDao = new CreatureRemoteDao(this);
    }

    public LiveData<List<Creature>> getCreatures() {
        return creaturesLiveData;
    }

    public CreatureRemoteDao creatureRemoteDao() {
        return creatureRemoteDao;
    }

    public static synchronized CreatureRemoteDatabase getInstance() {
        if (instance == null) {
            instance = new CreatureRemoteDatabase();
        }
        return instance;
    }

    public void _add(Creature creature) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference(Paths.getCreaturesPath(user.getUid())).push().setValue(creature, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                }
            });
        }
    }

    public void _insert(String which, Creature creature) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference(Paths.getCreaturePath(user.getUid(), which)).setValue(creature, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                }
            });
        }
    }

    public void _update(String which, Creature creature) {
        _insert(which, creature);
    }

    public void _delete(String which) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseDatabase.getInstance().getReference(Paths.getCreaturePath(user.getUid(), which)).setValue(null);
        }
    }

    private class Conversions implements Function<DataSnapshot, List<Creature>> {

        @Override
        public List<Creature> apply(DataSnapshot snapshot) {
            List<Creature> creatures = new ArrayList<>();
            for (DataSnapshot snap : snapshot.getChildren()) {
                creatures.add(snap.getValue(Creature.class));
            }
            return creatures;
        }
    }
}
