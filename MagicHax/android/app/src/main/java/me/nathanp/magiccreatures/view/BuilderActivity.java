package me.nathanp.magiccreatures.view;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;
import me.nathanp.magiccreatures.model.Creature;
import me.nathanp.magiccreatures.R;
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel;

public class BuilderActivity extends AppCompatActivity {
    private static final String TAG = "BuilderActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_builder);

        CreatureBuilderViewModel creatureBuilderViewModel = ViewModelProviders.of(this).get(CreatureBuilderViewModel.class);

        if (getIntent().hasExtra("creature")) {
            String creatureJson = getIntent().getStringExtra("creature");
            creatureBuilderViewModel.setCreature(Creature.fromJson(creatureJson));
        } else {
            creatureBuilderViewModel.setCreature(new Creature());
        }

        ViewPager pager = findViewById(R.id.builder_pager);
        setupBuilderPager(pager);

        TabLayout tabLayout = findViewById(R.id.builder_tabs);
        tabLayout.setupWithViewPager(pager);
    }

    private void setupBuilderPager(ViewPager viewPager) {
        BuilderFragmentsAdapter builderFragmentsAdapter = new BuilderFragmentsAdapter(getSupportFragmentManager());
        builderFragmentsAdapter.addFragment(new BuilderFragmentTypes(), "Types");
        builderFragmentsAdapter.addFragment(new BuilderFragmentTraits(), "Traits");
        builderFragmentsAdapter.addFragment(new BuilderFragmentStats(), "Stats");
        builderFragmentsAdapter.addFragment(new BuilderFragmentCards(), "Cards");
        viewPager.setAdapter(builderFragmentsAdapter);
    }

    private class BuilderFragmentsAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> mFragments = new ArrayList<>();
        private ArrayList<String> mFragmentTitles = new ArrayList<>();

        public BuilderFragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String name) {
            mFragments.add(fragment);
            mFragmentTitles.add(name);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
