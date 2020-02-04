package me.nathanp.magiccreatures.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import me.nathanp.magiccreatures.model.Creature
import me.nathanp.magiccreatures.model.CreatureRepository

class CreatureListViewModel(application: Application) : AndroidViewModel(application) {
    private val creatureRepository = CreatureRepository()
    fun add(creature: Creature) {
        creatureRepository.addCreature(creature)
    }

    fun update(which: String, creature: Creature) {
        creatureRepository.addCreature(creature)
    }

    fun delete(which: String) {
        creatureRepository.deleteCreature(which)
    }

    val allCreatures: LiveData<List<Creature>>
        get() = creatureRepository.allCreatures

    companion object {
        private const val TAG = "CreatureListViewModel"
    }
}