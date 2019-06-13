package me.nathanp.magiccreatures.model;

import java.util.List;

import androidx.lifecycle.LiveData;

public class CardRepository {
    private static final String TAG = "CardRepository";
    private CardsRemoteDao remoteDao;

    public CardRepository() {
        remoteDao = new CardsRemoteDao();
    }

    public LiveData<List<Card>> getAllCards() {
        return remoteDao.getAllCards();
    }
}
