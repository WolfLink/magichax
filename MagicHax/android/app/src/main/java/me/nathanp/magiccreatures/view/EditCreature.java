package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.CreatureResources;

public class EditCreature extends Fragment {

    Creature mCreature;
    private int mType1 = 0;
    private int mType2 = 0;
    private ImageView mCreatureImage;

    private static int getTypeFromRadioId(int id) {
        if (id == R.id.fire_check_1        || id == R.id.fire_check_2)        return 0;
        if (id == R.id.water_check_1       || id == R.id.water_check_2)       return 1;
        if (id == R.id.earth_check_1       || id == R.id.earth_check_2)       return 2;
        if (id == R.id.air_check_1         || id == R.id.air_check_2)         return 3;
        if (id == R.id.plant_check_1       || id == R.id.plant_check_2)       return 4;
        if (id == R.id.electricity_check_1 || id == R.id.electricity_check_2) return 5;
        return -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {
        View view = inflater.inflate(R.layout.edit_creature, container, false);

        RadioGroup group1 = view.findViewById(R.id.group1);
        RadioGroup group2 = view.findViewById(R.id.group2);
        mCreatureImage = view.findViewById(R.id.creature_image);

        group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mType1 = getTypeFromRadioId(checkedId);
                changeCreatureImage();
            }
        });

        group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mType2 = getTypeFromRadioId(checkedId);
                changeCreatureImage();
            }
        });
        return view;
    }

    private void changeCreatureImage() {
        int drawableId = CreatureResources.getCreatureResource(mType1, mType2);
        mCreatureImage.setImageResource(drawableId);
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mCreature = (Creature) bundle.get("creature");
        }
    }
}
