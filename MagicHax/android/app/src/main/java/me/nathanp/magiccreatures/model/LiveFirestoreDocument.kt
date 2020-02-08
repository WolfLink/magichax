package me.nathanp.magiccreatures.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.firestore.*

class LiveFirestoreDocument<T>(private var ref: DocumentReference, private val clazz: Class<T>) : LiveData<T>() {
    private val TAG = "LiveFirestoreDocument"
    private val listener = LiveDocumentEventListener()
    private lateinit var registration: ListenerRegistration
    fun changeRef(ref: DocumentReference) {
        registration.remove()
        this.ref = ref
        registration = this.ref.addSnapshotListener(listener)
    }

    override fun onActive() {
        registration = ref.addSnapshotListener(listener)
    }

    override fun onInactive() {
        registration.remove()
    }

    private inner class LiveDocumentEventListener : EventListener<DocumentSnapshot?> {
        override fun onEvent(snapshot: DocumentSnapshot?, e: FirebaseFirestoreException?) {
            e?.run {
                Log.e(TAG, "onEvent: error", this)
                return
            }
            if (snapshot?.exists() == true) {
                try {
                    value = snapshot.toObject(clazz)
                } catch (re: RuntimeException) {
                    Log.e(TAG, "Couldn't convert snapshot to object: ${re.message}")
                }
            }
        }
    }
}