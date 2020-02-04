package me.nathanp.magiccreatures.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.ActivityBuilderBinding
import me.nathanp.magiccreatures.model.Creature
import me.nathanp.magiccreatures.viewmodel.CreatureBuilderViewModel
import java.util.*

class BuilderActivity : AppCompatActivity() {

    lateinit var binding: ActivityBuilderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_builder)

        val creatureBuilderViewModel = ViewModelProvider(this).get(CreatureBuilderViewModel::class.java)
        if (intent.hasExtra("creature")) {
            val creature = intent.getSerializableExtra("creature") as Creature
            creatureBuilderViewModel.setCreature(creature)
        } else {
            creatureBuilderViewModel.setCreature(Creature())
        }
        setupBuilderPager(binding.builderPager)
        binding.builderTabs.setupWithViewPager(binding.builderPager)
    }

    private fun setupBuilderPager(viewPager: ViewPager) {
        val builderFragmentsAdapter = BuilderFragmentsAdapter(supportFragmentManager)
        builderFragmentsAdapter.addFragment(BuilderFragmentTypes(), "Types")
        builderFragmentsAdapter.addFragment(BuilderFragmentTraits(), "Traits")
        builderFragmentsAdapter.addFragment(BuilderFragmentStats(), "Stats")
        builderFragmentsAdapter.addFragment(BuilderFragmentCards(), "Cards")
        viewPager.adapter = builderFragmentsAdapter
    }

    private inner class BuilderFragmentsAdapter internal constructor(fm: FragmentManager?) : FragmentPagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragments = ArrayList<Fragment>()
        private val mFragmentTitles = ArrayList<String>()
        fun addFragment(fragment: Fragment, name: String) {
            mFragments.add(fragment)
            mFragmentTitles.add(name)
        }

        override fun getItem(position: Int) = mFragments[position]
        override fun getCount() = mFragments.size
        override fun getPageTitle(position: Int) = mFragmentTitles[position]
    }

    companion object {
        private const val TAG = "BuilderActivity"
    }
}