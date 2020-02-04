package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData

class CreatureRepository {
    private val remoteDao = CreatureRemoteDao()
    val allCreatures: LiveData<List<Creature>>
        get() = remoteDao.allCreatures

    fun addCreature(creature: Creature): String {
        return remoteDao.add(creature)
    }

    fun deleteCreature(id: String) {
        remoteDao.delete(id)
    }
}