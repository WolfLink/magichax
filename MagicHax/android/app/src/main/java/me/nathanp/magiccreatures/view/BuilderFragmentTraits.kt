package me.nathanp.magiccreatures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import java.util.ArrayList
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.FragmentBuilderTraitsBinding
import me.nathanp.magiccreatures.model.Trait
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel

class BuilderFragmentTraits : Fragment() {
    private val TAG = javaClass.simpleName

    private lateinit var creatureBuilderViewModel: CreatureBuilderViewModel
    private lateinit var traitWrapperCardAdapter: CardAdapter<TraitWrapper>

    private inner class TraitWrapper internal constructor(private val trait: Trait) : CardAdapter.CardInfo {
        override val drawableId: Int
            get() = trait.drawableId
        override val name: String
            get() = trait.name
        override val description: String
            get() = "${trait.description} \nCost: ${trait.cost}"
        override val isCheckable: Boolean
            get() = true
        override var checked: Boolean
            get() = creatureBuilderViewModel.hasTrait(trait)
            set(value) {
                if (value) {
                    creatureBuilderViewModel.selectTrait(trait)
                } else {
                    creatureBuilderViewModel.unselectTrait(trait)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creatureBuilderViewModel = ViewModelProvider(activity!!).get(CreatureBuilderViewModel::class.java)

        creatureBuilderViewModel.traitMap.observe(this, Observer { traits ->
            val wrappers = ArrayList<TraitWrapper>(traits.size)
            for (t in traits.values) {
                wrappers.add(TraitWrapper(t))
            }
            traitWrapperCardAdapter.things = wrappers
        })


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentBuilderTraitsBinding>(inflater, R.layout.fragment_builder_traits, container, false)
        binding.lifecycleOwner = activity

        val traits = binding.traits
        traits.layoutManager = LinearLayoutManager(context)

        traitWrapperCardAdapter = object : CardAdapter<TraitWrapper>() {
            override fun onSelected(pos: Int, item: TraitWrapper) {

            }
        }

        traits.adapter = traitWrapperCardAdapter
        return binding.root
    }
}
