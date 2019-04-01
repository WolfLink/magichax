package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

    private int mType1 = 0;
    private int mType2 = 0;

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
                mType1 = getTypeFromRadioId(checkedId);
                changeCreatureImage();
            }
        });

        mGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                mType2 = getTypeFromRadioId(checkedId);
                changeCreatureImage();
            }
        });

        if (getIntent().hasExtra("creature")) {
            String creatureJson = getIntent().getStringExtra("creature");
            loadFromCreature(Creature.fromJson(creatureJson));
        }
    }

    private void changeCreatureImage() {
        int drawableId = CreatureResources.getCreatureResource(mType1, mType2);
        mCreatureImage.setImageResource(drawableId);
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

    private void loadFromCreature(Creature creature) {
        mType1 = creature.getType1();
        mType2 = creature.getType2();
        mGroup1.check(getRadioIdFromType1(mType1));
        mGroup2.check(getRadioIdFromType2(mType2));
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
