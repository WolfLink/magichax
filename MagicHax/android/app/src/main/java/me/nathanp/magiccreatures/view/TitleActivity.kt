package me.nathanp.magiccreatures.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.ActivityTitleBinding
import me.nathanp.magiccreatures.model.API
import me.nathanp.magiccreatures.model.API.Then
import me.nathanp.magiccreatures.view.MainMenuActivity

class TitleActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityTitleBinding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        binding.signInButton.setOnClickListener { signIn() }
        binding.testBtn.setOnClickListener { testFunction() }
        binding.signOutBtn.setOnClickListener { signOut() }
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        FirebaseAuth.getInstance().addAuthStateListener {
            it.currentUser?.run { readyToPlay() }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct!!.id)
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "signInWithCredential:failure", task.exception)
                        Snackbar.make(findViewById(R.id.container), "Failed to sign in", Snackbar.LENGTH_SHORT).show()
                    }
                }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    fun testFunction() {
        API.testFunction(object : Then<String>() {
            override fun ok(value: String) {
                Snackbar.make(findViewById(R.id.container), value, Snackbar.LENGTH_SHORT).show()
            }

            public override fun cancelled(status: String) {
                Snackbar.make(findViewById(R.id.container), status, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun readyToPlay() {
        val letsPlay = Intent(this, MainMenuActivity::class.java)
        startActivity(letsPlay)
        finish()
    }

    companion object {
        private const val TAG = "TitleActivity"
        private const val RC_SIGN_IN = 1
    }
}