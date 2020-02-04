package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class CreatureRemoteDao {
    private val TAG = javaClass.simpleName

    private val liveCreatureCollection: LiveFirestoreCollection<Creature> = FirebaseAuth.getInstance().currentUser?.let {
        LiveFirestoreCollection(FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath(it.uid)), Creature::class.java)
    } ?: throw Exception("Trying to get creatures before a user is initialized")

    val allCreatures: LiveData<List<Creature>>
        get() = liveCreatureCollection

    fun add(creature: Creature): String {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.let {
            val newDoc = FirebaseFirestore.getInstance().collection(Paths.getPlayerCreaturesCollectionPath(it.uid)).document()
            newDoc.set(creature).addOnCompleteListener { }

            newDoc.id
        } ?: throw Exception("Tried to save a creature to a non-existent user")
    }

    fun delete(which: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val inst = FirebaseFirestore.getInstance()
        user?.run {
            inst.collection(Paths.getPlayerCreaturesCollectionPath(uid)).document(which).delete()
        }
    }
}