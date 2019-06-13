package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.CreatureResources;
import me.nathanp.magiccreatures.model.Types;
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel;

public class BuilderFragmentTypes extends Fragment {
    private static final String TAG = "BuilderFragmentTypes";

    private CreatureBuilderViewModel creatureBuilderViewModel;
    private Creature mCreature;

    private RadioGroup typeRadioGroup1;
    private RadioGroup typeRadioGroup2;
    private ImageView creatureImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        creatureBuilderViewModel = ViewModelProviders.of(getActivity()).get(CreatureBuilderViewModel.class);
        creatureBuilderViewModel.getCreature().observe(this, new Observer<Creature>() {

            @Override
            public void onChanged(Creature creature) {
                mCreature = creature;
                refreshUI();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_builder_types, container, false);

        typeRadioGroup1 = view.findViewById(R.id.group1);
        typeRadioGroup2 = view.findViewById(R.id.group2);
        creatureImageView = view.findViewById(R.id.creature_image);

        typeRadioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mCreature != null) {
                    mCreature.setType1(getTypeFromRadioId(checkedId));
                    creatureBuilderViewModel.setCreature(mCreature);
                }
            }
        });

        typeRadioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mCreature != null) {
                    mCreature.setType2(getTypeFromRadioId(checkedId));
                    creatureBuilderViewModel.setCreature(mCreature);
                }
            }
        });

        return view;
    }

    void refreshUI() {
        typeRadioGroup1.check(getRadioIdFromType1(mCreature.getType1()));
        typeRadioGroup2.check(getRadioIdFromType2(mCreature.getType2()));
        creatureImageView.setImageDrawable(getContext().getDrawable(CreatureResources.getCreatureResource(mCreature.getType1(), mCreature.getType2())));
    }

    private static int getTypeFromRadioId(int id) {
        if (id == R.id.fire_check_1        || id == R.id.fire_check_2)        return Types.TYPE_FIRE;
        if (id == R.id.water_check_1       || id == R.id.water_check_2)       return Types.TYPE_WATER;
        if (id == R.id.earth_check_1       || id == R.id.earth_check_2)       return Types.TYPE_EARTH;
        if (id == R.id.air_check_1         || id == R.id.air_check_2)         return Types.TYPE_AIR;
        if (id == R.id.plant_check_1       || id == R.id.plant_check_2)       return Types.TYPE_PLANT;
        if (id == R.id.electricity_check_1 || id == R.id.electricity_check_2) return Types.TYPE_ELECTRICITY;
        return -1;
    }

    private static int getRadioIdFromType1(int type1) {
        if (type1 == Types.TYPE_FIRE) return R.id.fire_check_1;
        if (type1 == Types.TYPE_WATER) return R.id.water_check_1;
        if (type1 == Types.TYPE_EARTH) return R.id.earth_check_1;
        if (type1 == Types.TYPE_AIR) return R.id.air_check_1;
        if (type1 == Types.TYPE_PLANT) return R.id.plant_check_1;
        if (type1 == Types.TYPE_ELECTRICITY) return R.id.electricity_check_1;
        return -1;
    }

    private static int getRadioIdFromType2(int type2) {
        if (type2 == Types.TYPE_FIRE) return R.id.fire_check_2;
        if (type2 == Types.TYPE_WATER) return R.id.water_check_2;
        if (type2 == Types.TYPE_EARTH) return R.id.earth_check_2;
        if (type2 == Types.TYPE_AIR) return R.id.air_check_2;
        if (type2 == Types.TYPE_PLANT) return R.id.plant_check_2;
        if (type2 == Types.TYPE_ELECTRICITY) return R.id.electricity_check_2;
        return -1;
    }
}
