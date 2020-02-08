package me.nathanp.magiccreatures.model

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.FirebaseFunctionsException
import me.nathanp.magiccreatures.model.Paths.getPlayerCreaturesCollectionPath
import me.nathanp.magiccreatures.model.Paths.getPlayerCurrentGamePath
import me.nathanp.magiccreatures.model.Paths.getPlayerDocumentPath
import java.util.*

object API {
    const val OK = 0
    const val CANCELLED = 1
    const val UNKNOWN = 2
    const val INVALID_ARGUMENT = 3
    const val DEADLINE_EXCEEDED = 4
    const val NOT_FOUND = 5
    const val ALREADY_EXISTS = 6
    const val PERMISSION_DENIED = 7
    const val RESOURCE_EXHAUSTED = 8
    const val FAILED_PRECONDITION = 9
    const val ABORTED = 10
    const val OUT_OF_RANGE = 11
    const val UNIMPLEMENTED = 12
    const val INTERNAL = 13
    const val UNAVAILABLE = 14
    const val DATA_LOSS = 15
    const val UNAUTHENTICATED = 16
    private fun translateCode(code: FirebaseFunctionsException.Code): Int {
        return when (code) {
            FirebaseFunctionsException.Code.OK -> OK
            FirebaseFunctionsException.Code.CANCELLED -> CANCELLED
            FirebaseFunctionsException.Code.UNKNOWN -> UNKNOWN
            FirebaseFunctionsException.Code.INVALID_ARGUMENT -> INVALID_ARGUMENT
            FirebaseFunctionsException.Code.DEADLINE_EXCEEDED -> DEADLINE_EXCEEDED
            FirebaseFunctionsException.Code.NOT_FOUND -> NOT_FOUND
            FirebaseFunctionsException.Code.ALREADY_EXISTS -> ALREADY_EXISTS
            FirebaseFunctionsException.Code.PERMISSION_DENIED -> PERMISSION_DENIED
            FirebaseFunctionsException.Code.RESOURCE_EXHAUSTED -> RESOURCE_EXHAUSTED
            FirebaseFunctionsException.Code.FAILED_PRECONDITION -> FAILED_PRECONDITION
            FirebaseFunctionsException.Code.ABORTED -> ABORTED
            FirebaseFunctionsException.Code.OUT_OF_RANGE -> OUT_OF_RANGE
            FirebaseFunctionsException.Code.UNIMPLEMENTED -> UNIMPLEMENTED
            FirebaseFunctionsException.Code.INTERNAL -> INTERNAL
            FirebaseFunctionsException.Code.UNAVAILABLE -> UNAVAILABLE
            FirebaseFunctionsException.Code.DATA_LOSS -> DATA_LOSS
            FirebaseFunctionsException.Code.UNAUTHENTICATED -> UNAUTHENTICATED
            else -> UNKNOWN
        }
    }

    fun codeToString(code: Int): String {
        return when (code) {
            OK -> "OK"
            CANCELLED -> "CANCELLED"
            UNKNOWN -> "UNKNOWN"
            INVALID_ARGUMENT -> "INVALID_ARGUMENT"
            DEADLINE_EXCEEDED -> "DEADLINE_EXCEEDED"
            NOT_FOUND -> "NOT_FOUND"
            ALREADY_EXISTS -> "ALREADY_EXISTS"
            PERMISSION_DENIED -> "PERMISSION_DENIED"
            RESOURCE_EXHAUSTED -> "RESOURCE_EXHAUSTED"
            FAILED_PRECONDITION -> "FAILED_PRECONDITION"
            ABORTED -> "ABORTED"
            OUT_OF_RANGE -> "OUT_OF_RANGE"
            UNIMPLEMENTED -> "UNIMPLEMENTED"
            INTERNAL -> "INTERNAL"
            UNAVAILABLE -> "UNAVAILABLE"
            DATA_LOSS -> "DATA_LOSS"
            UNAUTHENTICATED -> "UNAUTHENTICATED"
            else -> "UNKNOWN"
        }
    }

    fun testFunction(then: Then<String>) {
        val functions = FirebaseFunctions.getInstance()
        functions.getHttpsCallable("testFunction").call()
                .continueWith { task -> task.result!!.data.toString() }.addOnCompleteListener(object : APICompleteListener<String>() {
                    override fun apiComplete(value: String) {
                        then.ok(value)
                    }

                    override fun apiFail(status: Int) {
                        then.cancelled(codeToString(status))
                    }
                })
    }

    fun isReadyToPlay(then: Then<Boolean?>) {
        val functions = FirebaseFunctions.getInstance()
        functions.getHttpsCallable("isReadyToPlay").call()
                .continueWith { task ->
                    if (task.result != null) {
                        val result = task.result!!.data as String?
                        result == "Yes"
                    } else {
                        false
                    }
                }.addOnCompleteListener(object : APICompleteListener<Boolean>() {
                    override fun apiComplete(value: Boolean) {
                        then.ok(value)
                    }

                    override fun apiFail(status: Int) {
                        then.cancelled(codeToString(status))
                    }
                })
    }

    fun getAvailableCards(player: Player, then: Then<ArrayList<Card>>) {
        val cards = ArrayList<Card>()
        val database = FirebaseDatabase.getInstance()
        val cardsRef = database.getReference("cards")
        for (card in player.cards) {
            cardsRef.child(card).addListenerForSingleValueEvent(object : ValueEventListener<Card>(Card::class.java) {
                override fun onEvent(data: Card) {
                    cards.add(data)
                }
            })
        }
        then.ok(cards)
    }

    fun getCardCosts(then: Then<HashMap<String?, Int>?>) {
        val database = FirebaseDatabase.getInstance()
        val cardCostRef = database.getReference("cardCosts")
        cardCostRef.addListenerForSingleValueEvent(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val result = HashMap<String?, Int>()
                for (ds in dataSnapshot.children) {
                    result[ds.key] = ds.getValue(Int::class.java)!!
                }
                then.ok(result)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                then.cancelled(databaseError.toString())
            }
        })
    }

    fun getPlayer(uid: String?, listener: ValueEventListener<*>?) {
        val database = FirebaseDatabase.getInstance()
        database.getReference(getPlayerDocumentPath(uid!!)).addListenerForSingleValueEvent(listener!!)
    }

    fun getCreature(then: Then<Creature>) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val database = FirebaseDatabase.getInstance()
            database.getReference(getPlayerCreaturesCollectionPath(it.uid)).addListenerForSingleValueEvent(object : ValueEventListener<Creature>(Creature::class.java) {
                override fun onEvent(data: Creature) {
                    then.ok(data)
                }
            })
        }
    }

    fun getCurrentGame(uid: String, listener: ValueEventListener<*>) {
        val database = FirebaseDatabase.getInstance()
        database.getReference(getPlayerDocumentPath(uid)).addListenerForSingleValueEvent(listener)
    }

    fun setOnCurrentGameChangedListener(uid: String, listener: ValueEventListener<*>) {
        val database = FirebaseDatabase.getInstance()
        database.getReference(getPlayerCurrentGamePath(uid)).addValueEventListener(listener)
    }

    abstract class Then<T> {
        abstract fun ok(data: T)
        abstract fun cancelled(reason: String)
    }

    abstract class APICompleteListener<T> : OnCompleteListener<T> {
        override fun onComplete(task: Task<T>) {
            if (task.isSuccessful) {
                val result: T? = task.result
                result?.also { apiComplete(it) }
            } else {
                val e = task.exception
                if (e is FirebaseFunctionsException) {
                    apiFail(translateCode(e.code))
                }
            }
        }
        abstract fun apiComplete(value: T)
        abstract fun apiFail(status: Int)
    }

    abstract class ValueEventListener<T> protected constructor(private val clazz: Class<T>) : com.google.firebase.database.ValueEventListener {
        abstract fun onEvent(data: T)
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val o: T? = if (dataSnapshot.exists()) dataSnapshot.getValue(clazz) else null
            o?.also { onEvent(it) }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }
}