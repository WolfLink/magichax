package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData

class CardRepository {
    private val TAG = "CardRepository"
    private val remoteDao = CardsRemoteDao()
    val allCards: LiveData<List<Card>>
        get() = remoteDao.allCards
}