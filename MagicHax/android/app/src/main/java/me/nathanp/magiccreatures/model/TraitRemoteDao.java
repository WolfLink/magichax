package me.nathanp.magiccreatures.model;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import androidx.lifecycle.LiveData;

class TraitRemoteDao {

    private LiveFirestoreCollection<Trait> liveTraitCollection;

    public TraitRemoteDao() {
        liveTraitCollection = new LiveFirestoreCollection<>(FirebaseFirestore.getInstance().collection(Paths.getTraitCollectionPath()), Trait.class);
    }

    public LiveData<List<Trait>> getAllTraits() {
        return liveTraitCollection;
    }
}
