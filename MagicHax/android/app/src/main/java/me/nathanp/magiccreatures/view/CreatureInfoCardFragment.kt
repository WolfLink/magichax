package me.nathanp.magiccreatures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.FragmentCreatureInfoCardBinding
import me.nathanp.magiccreatures.databinding.TraitItemBinding
import me.nathanp.magiccreatures.model.Trait
import me.nathanp.magiccreatures.view.CreatureInfoCardFragment.TraitIconAdapter.TraitIconHolder
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel

class CreatureInfoCardFragment : Fragment() {
    private val TAG = javaClass.simpleName
    private lateinit var creatureBuilderViewModel: CreatureBuilderViewModel
    private lateinit var traitIconAdapter: TraitIconAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creatureBuilderViewModel = activity?.run {
             ViewModelProvider(this).get(CreatureBuilderViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        traitIconAdapter = TraitIconAdapter()

        creatureBuilderViewModel.creature.observe(this, Observer {
            creatureBuilderViewModel.traitMap.value?.run {
                val traits: MutableList<Trait> = mutableListOf()
                for (trait in it.traits) {
                    get(trait)?.run {
                        traits.add(this)
                    }
                }
                traitIconAdapter.traits = traits
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentCreatureInfoCardBinding>(inflater, R.layout.fragment_creature_info_card, container, false)
        binding.lifecycleOwner = activity
        binding.viewmodel = creatureBuilderViewModel
        binding.creatureInfoTraitList.adapter = traitIconAdapter
        return binding.root
    }

    private inner class TraitIconAdapter : RecyclerView.Adapter<TraitIconHolder>() {

        var traits: List<Trait> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TraitIconHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = TraitItemBinding.inflate(inflater, parent, false)
            return TraitIconHolder(binding)
        }

        override fun onBindViewHolder(holder: TraitIconHolder, position: Int) {
            holder.bind(traits[position])
        }

        override fun getItemCount(): Int {
            return traits.size
        }

        private inner class TraitIconHolder internal constructor(var binding: TraitItemBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(trait: Trait?) {
                binding.trait = trait
                binding.executePendingBindings()
            }

        }
    }
}