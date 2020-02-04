package me.nathanp.magiccreatures.model;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class LiveDatabase<T> extends LiveData<T> {
    private static final String TAG = "LiveDatabase";

    private DatabaseReference ref;
    private Class<T> clazz;
    private final LiveDatabaseListener listener = new LiveDatabaseListener();

    public LiveDatabase(DatabaseReference ref, Class<T> clazz) {
        this.ref = ref;
        this.clazz = clazz;
    }

    @Override
    protected void onActive() {
        ref.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        ref.removeEventListener(listener);
    }

    private class LiveDatabaseListener implements ValueEventListener {

        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            setValue(dataSnapshot.getValue(clazz));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, databaseError.getMessage());
        }
    }
}
