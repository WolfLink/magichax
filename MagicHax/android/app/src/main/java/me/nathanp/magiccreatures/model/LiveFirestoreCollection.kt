package me.nathanp.magiccreatures.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*
import java.lang.Exception

class LiveFirestoreCollection<T>(private var ref: CollectionReference, private val clazz: Class<T>) : LiveData<List<T>>() {
    private val TAG = "LiveFirestoreCollection"
    private val listener = LiveCollectionEventListener()
    private lateinit var registration: ListenerRegistration

    fun changeRef(ref: CollectionReference) {
        registration.remove()
        this.ref = ref
        this.ref.addSnapshotListener(listener)
    }

    override fun onActive() {
        registration = ref.addSnapshotListener(listener)
    }

    override fun onInactive() {
        registration.remove()
    }

    private inner class LiveCollectionEventListener : EventListener<QuerySnapshot> {
        override fun onEvent(queryDocumentSnapshots: QuerySnapshot?, e: FirebaseFirestoreException?) {
            e?.let { throw it }
            queryDocumentSnapshots?.run {
                val things: MutableList<T> = mutableListOf()
                for (snapshot in this) {
                    try {
                        things.add(snapshot.toObject(clazz))
                    } catch (re: RuntimeException) {
                        Log.e(TAG, "Couldn't convert snapshot to object.")
                        Log.e(TAG, re.message)
                    }
                }
                value = things
            }
        }
    }
}