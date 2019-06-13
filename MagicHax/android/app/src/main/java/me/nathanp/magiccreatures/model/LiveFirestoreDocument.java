package me.nathanp.magiccreatures.model;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import javax.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class LiveFirestoreDocument<T> extends LiveData<T> {
    private static final String TAG = "LiveFirestoreDocument";

    private DocumentReference ref;
    private Class<T> clazz;
    private final LiveDocumentEventListener listener = new LiveDocumentEventListener();
    private ListenerRegistration registration;

    public LiveFirestoreDocument(DocumentReference ref, Class<T> clazz) {
        this.ref = ref;
        this.clazz = clazz;
    }

    public void changeRef(DocumentReference ref) {
        registration.remove();
        this.ref = ref;
        registration = this.ref.addSnapshotListener(listener);
    }

    @Override
    protected void onActive() {
        registration = ref.addSnapshotListener(listener);
    }

    @Override
    protected void onInactive() {
        registration.remove();
    }

    private class LiveDocumentEventListener implements EventListener<DocumentSnapshot> {

        @Override
        public void onEvent(@Nullable DocumentSnapshot snapshot,
                            @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.e(TAG, "onEvent: error", e);
                return;
            }
            if (snapshot != null && snapshot.exists()) {
                try {
                    setValue(snapshot.toObject(clazz));
                } catch (RuntimeException re) {
                    Log.e(TAG, "Couldn't convert snapshot to object");
                    Log.e(TAG, re.getMessage());
                }
            }
        }
    }
}
