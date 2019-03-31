package me.nathanp.magiccreatures.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import me.nathanp.magiccreatures.model.API;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.R;

public class BuilderActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "BuilderActivity";
    Creature.Creatures mCreatures;
    Map<String, Integer> cardCosts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);
        ImageButton c1Btn = findViewById(R.id.c1);
        ImageButton c2Btn = findViewById(R.id.c2);
        ImageButton c3Btn = findViewById(R.id.c3);

        c1Btn.setOnClickListener(this);
        c2Btn.setOnClickListener(this);
        c3Btn.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.c1) {
            //editCreature(c1)
        } else if (v.getId() == R.id.c2) {
            //editCreature(c2)
        } else if (v.getId() == R.id.c3) {
            //editCreature(c3)
        }
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
