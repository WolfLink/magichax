package me.nathanp.magiccreatures.model;

import android.content.Context;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class CardRepository {
    private static final String TAG = "CardRepository";
    private CardsRemoteDao remoteDao;

    public CardRepository() {
        remoteDao = new CardsRemoteDao();
        remoteDao.getAllCards().observeForever(new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {

            }
        });
    }

    public LiveData<List<Card>> getAllCards() {
        return remoteDao.getAllCards();
    }
}
