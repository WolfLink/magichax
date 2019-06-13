package me.nathanp.magiccreatures.model;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.lifecycle.LiveData;

class CardsRemoteDao {
    private static final String TAG = "CardsRemoteDao";

    private LiveFirestoreCollection<Card> liveCardCollection;

    public CardsRemoteDao() {
        Log.d(TAG, Paths.getCardCollectionPath());
        liveCardCollection = new LiveFirestoreCollection<>(FirebaseFirestore.getInstance().collection(Paths.getCardCollectionPath()), Card.class);
    }

    public LiveData<List<Card>> getAllCards() {
        return liveCardCollection;
    }
}
