package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import me.nathanp.magiccreatures.model.API;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.model.CreatureResources;

public class BuilderActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "BuilderActivity";

    Map<String, Integer> cardCosts = new HashMap<>();
    private RadioGroup mGroup1;
    private RadioGroup mGroup2;
    private ImageView mCreatureImage;
    private Creature mCreature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        mGroup1 = findViewById(R.id.group1);
        mGroup2 = findViewById(R.id.group2);
        mCreatureImage = findViewById(R.id.creature_image);

        API.getCardCosts(new API.Then<HashMap<String, Integer>>() {
            @Override
            protected void ok(HashMap<String, Integer> data) {
                cardCosts = data;
                Log.d(TAG, data.toString());
            }

            @Override
            protected void cancelled(String reason) {
                // Uhhhh
            }
        });

        mGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCreature.setType1(getTypeFromRadioId(checkedId));
                refreshUI();
            }
        });

        mGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mCreature.setType2(getTypeFromRadioId(checkedId));
                refreshUI();
            }
        });

        if (getIntent().hasExtra("creature")) {
            String creatureJson = getIntent().getStringExtra("creature");
            mCreature = Creature.fromJson(creatureJson);
        } else {
            mCreature = new Creature();
        }
        refreshUI();
    }

    @Override
    public void onClick(View v) {

    }

    private static int getTypeFromRadioId(int id) {
        if (id == R.id.fire_check_1        || id == R.id.fire_check_2)        return 0;
        if (id == R.id.water_check_1       || id == R.id.water_check_2)       return 1;
        if (id == R.id.earth_check_1       || id == R.id.earth_check_2)       return 2;
        if (id == R.id.air_check_1         || id == R.id.air_check_2)         return 3;
        if (id == R.id.plant_check_1       || id == R.id.plant_check_2)       return 4;
        if (id == R.id.electricity_check_1 || id == R.id.electricity_check_2) return 5;
        return -1;
    }

    private static int getRadioIdFromType1(int type1) {
        if (type1 == 0) return R.id.fire_check_1;
        if (type1 == 1) return R.id.water_check_1;
        if (type1 == 2) return R.id.earth_check_1;
        if (type1 == 3) return R.id.air_check_1;
        if (type1 == 4) return R.id.plant_check_1;
        if (type1 == 5) return R.id.electricity_check_1;
        return -1;
    }

    private static int getRadioIdFromType2(int type2) {
        if (type2 == 0) return R.id.fire_check_2;
        if (type2 == 1) return R.id.water_check_2;
        if (type2 == 2) return R.id.earth_check_2;
        if (type2 == 3) return R.id.air_check_2;
        if (type2 == 4) return R.id.plant_check_2;
        if (type2 == 5) return R.id.electricity_check_2;
        return -1;
    }

    private void refreshUI() {
        int t1 = mCreature.getType1();
        int t2 = mCreature.getType2();
        mGroup1.check(getRadioIdFromType1(t1));
        mGroup2.check(getRadioIdFromType2(t2));
        int drawableId = CreatureResources.getCreatureResource(t1, t2);
        mCreatureImage.setImageResource(drawableId);
    }

    void validateAndSubmitCreature(Creature c) {
        API.validateAndSubmitCreature(c, new API.Then<String>() {
            @Override
            protected void ok(String data) {
                Snackbar.make(findViewById(R.id.container), data, Snackbar.LENGTH_LONG).show();
            }

            @Override
            protected void cancelled(String reason) {
                // RIP
            }
        });
    }
}
