package me.nathanp.magiccreatures.model

import androidx.lifecycle.MediatorLiveData
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query

class MediatorLiveFirebaseData : MediatorLiveData<DataSnapshot?>() {
    private val query: Query? = null
    private val datas: MutableList<DataSnapshot>? = null
    private val listener = LiveChildEventListener()

    private inner class LiveChildEventListener : ChildEventListener {
        override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
            datas!!.add(dataSnapshot)
        }

        override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
        override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
        override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
        override fun onCancelled(databaseError: DatabaseError) {}
    }
}