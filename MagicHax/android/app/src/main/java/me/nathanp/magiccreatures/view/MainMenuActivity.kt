package me.nathanp.magiccreatures.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.ActivityMainMenuBinding
import me.nathanp.magiccreatures.model.Creature
import me.nathanp.magiccreatures.view.CardAdapter.CardInfo
import me.nathanp.magiccreatures.viewmodel.CreatureListViewModel
import java.util.*

class MainMenuActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivityMainMenuBinding
    private lateinit var creatureListViewModel: CreatureListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu)
        setSupportActionBar(binding.bottomAppBar)
        mAuth = FirebaseAuth.getInstance()
        mAuth.addAuthStateListener {
            val user = mAuth.currentUser
            if (user == null) {
                gotoTitleScreen()
            } else {
                startUserSignInFlow()
            }
        }
        with(binding.creatures) {
            layoutManager = LinearLayoutManager(this@MainMenuActivity)
            setHasFixedSize(true)
            setItemViewCacheSize(20)
        }

        class CreatureWrapper(var creature: Creature) : CardInfo {
            override var drawableId = creature.drawableId
            override val name: String
                get() = creature.name
            override val description: String
                get() = creature.statSummary
            override val isCheckable: Boolean
                get() = false
            override var checked: Boolean
                get() = false
                set(value) {}
        }

        val adapter: CardAdapter<CreatureWrapper> = object : CardAdapter<CreatureWrapper>() {
            override fun onSelected(pos: Int, item: CreatureWrapper) {
                startBuilderActivity(item.creature)
            }
        }

        binding.creatures.adapter = adapter
        creatureListViewModel = ViewModelProvider(this).get(CreatureListViewModel::class.java)
        creatureListViewModel.allCreatures.observe(this, Observer { creatures ->
            val wrappers: MutableList<CreatureWrapper> = ArrayList(creatures.size)
            for (c in creatures) {
                wrappers.add(CreatureWrapper(c))
            }
            // Update view
            adapter.things = wrappers
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: " + item.itemId)
        when (item.itemId) {
            android.R.id.home -> return true
            R.id.bottom_add -> {
                startBuilderActivity(null)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startUserSignInFlow() {}
    private fun gotoTitleScreen() {
        val titleScreenIntent = Intent(this, TitleActivity::class.java)
        startActivity(titleScreenIntent)
        finish()
    }

    private fun startBuilderActivity(selectedCreature: Creature?) {
        val builderActivityIntent = Intent(this, BuilderActivity::class.java)
        selectedCreature?.run {
            builderActivityIntent.putExtra("creature", this)
        }
        startActivityForResult(builderActivityIntent, CREATURE_EDIT_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == CREATURE_EDIT_REQUEST_CODE) {
        }
    }

    companion object {
        private const val TAG = "GameActivity"
        const val CREATURE_EDIT_REQUEST_CODE = 1
    }
}