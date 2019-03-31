package me.nathanp.magiccreatures.model;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class LiveFirebaseData extends LiveData<DataSnapshot> {
    private static final String LOG_TAG = "LiveFirebaseData";
    private Query query;
    private final LiveValueEventListener listener = new LiveValueEventListener();

    public LiveFirebaseData(Query query) {
        this.query = query;
    }

    public LiveFirebaseData(DatabaseReference ref) {
        this.query = ref;
    }

    public void changeRef(DatabaseReference ref) {
        query.removeEventListener(listener);
        query = ref;
        query.addValueEventListener(listener);
    }

    @Override
    protected void onActive() {
        Log.d(LOG_TAG, "onActive");
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        Log.d(LOG_TAG, "onInactive");
        query.removeEventListener(listener);
    }

    private class LiveValueEventListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(LOG_TAG, "Can't listen to query " + query, databaseError.toException());
        }
    }
}
