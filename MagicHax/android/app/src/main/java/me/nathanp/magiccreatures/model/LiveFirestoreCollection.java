package me.nathanp.magiccreatures.model;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class LiveFirestoreCollection<T> extends LiveData<List<T>> {
    private static final String TAG = "LiveFirestoreCollection";

    private CollectionReference ref;
    private Class<T> clazz;
    private final LiveCollectionEventListener listener = new LiveCollectionEventListener();
    private ListenerRegistration registration;

    public LiveFirestoreCollection(CollectionReference ref, Class<T> clazz) {
        this.ref = ref;
        this.clazz = clazz;
    }

    public void changeRef(CollectionReference ref) {
        registration.remove();
        this.ref = ref;
        this.ref.addSnapshotListener(listener);
    }

    @Override
    protected void onActive() {
        registration = ref.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        registration.remove();
    }

    private class LiveCollectionEventListener implements EventListener<QuerySnapshot> {

        @Override
        public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.e(TAG, "onEvent: error", e);
                return;
            }
            if (queryDocumentSnapshots != null) {
                List<T> things = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    try {
                        things.add(snapshot.toObject(clazz));
                    } catch (RuntimeException re) {
                        Log.e(TAG, "Couldn't convert snapshot to object.");
                        Log.e(TAG, re.getMessage());
                    }
                }
                setValue(things);
            }
        }
    }
}
