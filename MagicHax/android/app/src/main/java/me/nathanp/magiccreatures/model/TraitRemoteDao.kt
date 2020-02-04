package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore

internal class TraitRemoteDao {
    private val liveTraitCollection: LiveFirestoreCollection<Trait> = LiveFirestoreCollection(FirebaseFirestore.getInstance().collection(Paths.getTraitCollectionPath()), Trait::class.java)
    val allTraits: LiveData<List<Trait>>
        get() = liveTraitCollection
}