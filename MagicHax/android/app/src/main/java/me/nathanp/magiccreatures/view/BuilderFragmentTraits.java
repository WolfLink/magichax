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
import me.nathanp.magiccreatures.model.Trait;
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel;

public class BuilderFragmentTraits extends Fragment {

    private CreatureBuilderViewModel creatureBuilderViewModel;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatureBuilderViewModel = ViewModelProviders.of(this).get(CreatureBuilderViewModel.class);
        creatureBuilderViewModel.getTraits().observe(this, new Observer<List<Trait>>() {
            @Override
            public void onChanged(List<Trait> traits) {
                refreshUI();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder_traits, container, false);
        // Init stuff from view
        return view;
    }

    private void refreshUI() {

    }
}
