package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.FirebaseFirestore

internal class CardsRemoteDao {
    private val TAG = javaClass.simpleName
    private val liveCardCollection = LiveFirestoreCollection(FirebaseFirestore.getInstance().collection(Paths.cardCollectionPath), Card::class.java)
    val allCards: LiveData<List<Card>>
        get() = liveCardCollection
}