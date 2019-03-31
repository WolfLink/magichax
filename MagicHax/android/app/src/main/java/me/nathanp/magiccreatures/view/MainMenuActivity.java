package me.nathanp.magiccreatures.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.model.CreatureAdapter;
import me.nathanp.magiccreatures.model.Paths;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.viewmodel.CreatureViewModel;

public class MainMenuActivity extends AppCompatActivity {
    private static final String LOG_TAG = "GameActivity";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mCurrentGameRef;
    DrawerLayout mDrawerLayout;
    BottomAppBar bottomAppBar;

    private CreatureViewModel creatureViewModel;

    com.google.firebase.database.ValueEventListener gameListener = new com.google.firebase.database.ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            // Todo: update the UI game state
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

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

        final CreatureAdapter adapter = new CreatureAdapter();
        recyclerView.setAdapter(adapter);

        creatureViewModel = ViewModelProviders.of(this).get(CreatureViewModel.class);
        LiveData<List<Creature>> creaturesLiveData = creatureViewModel.getAllCreatures();
        Log.w(LOG_TAG, creaturesLiveData.toString());
        creatureViewModel.getAllCreatures().observe(this, new Observer<List<Creature>>() {
            @Override
            public void onChanged(List<Creature> creatures) {
                // Update view
                adapter.setCreatures(creatures);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startUserSignInFlow() {

    }

    private void switchGameListener(String gameId) {
        mCurrentGameRef.removeEventListener(gameListener);
        mCurrentGameRef = mDatabase.getReference(Paths.getGamePath(gameId));
        mCurrentGameRef.addValueEventListener(gameListener);
    }

    private void gotoTitleScreen() {
        Intent titleScreenIntent = new Intent(this, TitleActivity.class);
        startActivity(titleScreenIntent);
        finish();
    }

    private void gotoBuilderActivity() {
        Intent builderActivityIntent = new Intent(this, BuilderActivity.class);
        startActivity(builderActivityIntent);
        finish();
    }
}