package me.nathanp.magiccreatures.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

class LiveDatabase<T>(private val ref: DatabaseReference, private val clazz: Class<T>) : LiveData<T>() {
    private val TAG = "LiveDatabase"
    private val listener = LiveDatabaseListener()
    override fun onActive() {
        ref.addValueEventListener(listener)
    }

    override fun onInactive() {
        ref.removeEventListener(listener)
    }

    private inner class LiveDatabaseListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            value = dataSnapshot.getValue(clazz)
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(TAG, databaseError.message)
        }
    }
}