package me.nathanp.magiccreatures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.FragmentBuilderTypesBinding
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel
import java.lang.Exception

class BuilderFragmentTypes : Fragment() {
    private val TAG = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val creatureBuilderViewModel = activity?.run { ViewModelProvider(activity!!).get(CreatureBuilderViewModel::class.java) } ?: throw Exception("No Activity")
        val binding = DataBindingUtil.inflate<FragmentBuilderTypesBinding>(inflater, R.layout.fragment_builder_types, container, false)
        binding.lifecycleOwner = activity
        binding.viewmodel = creatureBuilderViewModel
        return binding.root
    }
}