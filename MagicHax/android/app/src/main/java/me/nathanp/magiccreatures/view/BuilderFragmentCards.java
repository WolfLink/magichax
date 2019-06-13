package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.model.Card;
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel;

public class BuilderFragmentCards extends Fragment {
    private CreatureBuilderViewModel creatureBuilderViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatureBuilderViewModel = ViewModelProviders.of(this).get(CreatureBuilderViewModel.class);
        creatureBuilderViewModel.getCards().observe(this, new Observer<List<Card>>() {

            @Override
            public void onChanged(List<Card> cards) {
                refreshUI();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder_cards, container, false);
        // Do stuff
        return view;
    }

    private void refreshUI() {

    }
}
