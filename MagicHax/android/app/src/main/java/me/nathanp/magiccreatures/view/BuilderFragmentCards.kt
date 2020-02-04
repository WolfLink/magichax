package me.nathanp.magiccreatures.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel
import java.lang.Exception

class BuilderFragmentCards : Fragment() {
    private lateinit var creatureBuilderViewModel: CreatureBuilderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creatureBuilderViewModel = activity?.run {
            ViewModelProvider(this).get(CreatureBuilderViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        //        creatureBuilderViewModel.getCards().observe(this, new Observer<List<Card>>() {
//
//            @Override
//            public void onChanged(List<Card> cards) {
//                refreshUI();
//            }
//        });
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Do stuff
        return inflater.inflate(R.layout.fragment_builder_cards, container, false)
    }

    private fun refreshUI() {}
}