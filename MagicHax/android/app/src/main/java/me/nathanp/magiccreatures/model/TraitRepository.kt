package me.nathanp.magiccreatures.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class TraitRepository {
    private val remoteDao = TraitRemoteDao()

    val traitMap: LiveData<Map<String, Trait>>
        get() = Transformations.map(remoteDao.allTraits) { traits ->
            val traitMap: MutableMap<String, Trait> = sortedMapOf()
            for (trait in traits) {
                traitMap[trait.name] = trait
            }
            traitMap
        }
}