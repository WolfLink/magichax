package me.nathanp.magiccreatures.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import me.nathanp.magiccreatures.model.CardRepository
import me.nathanp.magiccreatures.model.Creature
import me.nathanp.magiccreatures.model.Resources.Types.getRadioIdFromType1
import me.nathanp.magiccreatures.model.Resources.Types.getRadioIdFromType2
import me.nathanp.magiccreatures.model.Resources.Types.getTypeFromRadioId
import me.nathanp.magiccreatures.model.Trait
import me.nathanp.magiccreatures.model.TraitRepository

class CreatureBuilderViewModel : ViewModel() {
    private val TAG = javaClass.simpleName
    private val startingPoints = 0
    private val cardRepository = CardRepository()
    private val traitRepository = TraitRepository()
    private val _creature = MutableLiveData<Creature>()
    private val _remainingPoints = MutableLiveData<Int>()
    // Public access to LiveData

    var creature: LiveData<Creature> = _creature
    var points: LiveData<Int> = _remainingPoints
    var cards = cardRepository.allCards
    var traitMap = traitRepository.traitMap

    var creatureImage = Transformations.map(_creature) { it.drawableId }

    fun setCreature(c: Creature?) {
        _creature.value = c
    }

    var type1: Int
        get() = getRadioIdFromType1(creature.value!!.type1)
        set(id) {
            _creature.value!!.type1 = getTypeFromRadioId(id)
            _creature.value = _creature.value
        }

    var type2: Int
        get() = getRadioIdFromType2(creature.value!!.type2)
        set(id) {
            _creature.value!!.type2 = getTypeFromRadioId(id)
            _creature.value = _creature.value
        }

    fun selectTrait(t: Trait): Boolean {
        val added = _creature.value!!.addTrait(t.name)
        if (added) _creature.value = _creature.value
        return added
    }

    fun unselectTrait(t: Trait): Boolean {
        val removed = _creature.value!!.removeTrait(t.name)
        if (removed) _creature.value = _creature.value
        return removed
    }

    fun hasTrait(t: Trait): Boolean {
        return _creature.value!!.hasTrait(t.name)
    }
}