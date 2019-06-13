package me.nathanp.magiccreatures.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.viewmodel.CreatureListViewModel;

public class MainMenuActivity extends AppCompatActivity {
    private static final String TAG = "GameActivity";
    public static final int CREATURE_EDIT_REQUEST_CODE = 1;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    BottomAppBar bottomAppBar;

    private CreatureListViewModel creatureListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setSupportActionBar(bottomAppBar);

        BottomAppBar bottomAppBar = findViewById(R.id.bottom_app_bar);
        setSupportActionBar(bottomAppBar);

        mAuth = FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user == null) {
                    gotoTitleScreen();
                } else {
                    mUser = user;
                    startUserSignInFlow();
                }
            }
        });

        RecyclerView recyclerView = findViewById(R.id.creatures);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);

        final CardAdapter adapter = new CardAdapter(new CardAdapter.OnCardSelected() {
            @Override
            public void onSelected(Creature creature) {
                Log.d(TAG, "onSelected: " + creature.toJson());
                startBuilderActivity(creature);
            }
        });
        recyclerView.setAdapter(adapter);

        creatureListViewModel = ViewModelProviders.of(this).get(CreatureListViewModel.class);
        creatureListViewModel.getAllCreatures().observe(this, new Observer<List<Creature>>() {
            @Override
            public void onChanged(List<Creature> creatures) {
                // Update view
                adapter.setThings(creatures);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                return true;
            case R.id.bottom_add:
                startBuilderActivity(null);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void startUserSignInFlow() {

    }

    private void gotoTitleScreen() {
        Intent titleScreenIntent = new Intent(this, TitleActivity.class);
        startActivity(titleScreenIntent);
        finish();
    }

    private void startBuilderActivity(Creature selectedCreature) {
        Intent builderActivityIntent = new Intent(this, BuilderActivity.class);
        if (selectedCreature != null) {
            builderActivityIntent.putExtra("creature", selectedCreature.toJson());
        }
        startActivityForResult(builderActivityIntent, CREATURE_EDIT_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == CREATURE_EDIT_REQUEST_CODE) {

        }
    }
}
