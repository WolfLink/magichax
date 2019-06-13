package me.nathanp.magiccreatures.viewmodel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import me.nathanp.magiccreatures.model.Card;
import me.nathanp.magiccreatures.model.CardRepository;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.Trait;
import me.nathanp.magiccreatures.model.TraitRepository;

public class CreatureBuilderViewModel extends ViewModel {

    private int startingPoints = 0;
    private Set<Trait> selectedTraits = new HashSet<>();
    private Set<Card> selectedCards = new HashSet<>();

    CardRepository cardRepository = new CardRepository();
    TraitRepository traitRepository = new TraitRepository();

    private MutableLiveData<Creature> mCreatureLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> remainingPointsLiveData = new MutableLiveData<>();

    private LiveData<ArrayList<Trait>> availableTraits;
    private LiveData<ArrayList<Card>> availableCards;

    public void setCreature(Creature creature) {
        mCreatureLiveData.setValue(creature);
    }

    public LiveData<Creature> getCreature() {
        return mCreatureLiveData;
    }

    public LiveData<List<Trait>> getTraits() {
        return traitRepository.getAllTraits();
    }

    public LiveData<List<Card>> getCards() {
        return cardRepository.getAllCards();
    }

    public void selectCard(Card c) {
        selectedCards.add(c);
        c.setSelected(true);

    }

    public void unselectCard(Card c) {
        selectedCards.remove(c);
        c.setSelected(false);
    }

    public void selectTrait(Trait t) {
        selectedTraits.add(t);
        t.setSelected(true);
    }

    public void unselectTrait(Trait t) {
        selectedTraits.remove(t);
        t.setSelected(false);
    }

    private int remainingPoints() {
        return startingPoints - totalCostOfTraits(selectedTraits) - totalCostOfCards(selectedCards);
    }

    private int totalCostOfCards(Set<Card> cards) {
        int total = 0;
        for (Card card : cards) {
            total += card.cost;
        }
        return total;
    }

    private int totalCostOfTraits(Set<Trait> traits) {
        int total = 0;
        for (Trait trait : traits) {
            total += trait.cost;
        }
        return total;
    }
}
