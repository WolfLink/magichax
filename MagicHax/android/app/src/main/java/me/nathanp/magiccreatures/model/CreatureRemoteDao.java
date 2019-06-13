package me.nathanp.magiccreatures.model;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class CreatureRemoteDao {

    private static final String TAG = "CreatureRemoteDao";
    private LiveFirestoreCollection<Creature> liveCreatureCollection;

    public CreatureRemoteDao() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Log.d(TAG, Paths.getPlayerCreaturesCollectionPath(user.getUid()));
            liveCreatureCollection = new LiveFirestoreCollection<>(FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath(user.getUid())), Creature.class);
        } else {
            liveCreatureCollection = new LiveFirestoreCollection<>(FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath("default")), Creature.class);
        }
    }

    public LiveData<List<Creature>> getAllCreatures() {
        return liveCreatureCollection;
    }

    public String add(Creature creature) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            DocumentReference newDoc = FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath(user.getUid())).document();
            newDoc.set(creature).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                }
            });
            return newDoc.getId();
        } else {
            return "";
        }
    }

    public void delete(String which) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath(user.getUid())).document(which).delete();
        }
    }
}
