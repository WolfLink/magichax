package me.nathanp.magiccreatures.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import me.nathanp.magiccreatures.R
import me.nathanp.magiccreatures.databinding.ActivityTitleBinding
import me.nathanp.magiccreatures.model.API
import me.nathanp.magiccreatures.model.API.Then

class TitleActivity : AppCompatActivity() {
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var binding: ActivityTitleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_title)
        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        binding.authHandler = this
        binding.signInButton.setOnClickListener { signIn() }
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        FirebaseAuth.getInstance().addAuthStateListener {
            it.currentUser?.run { readyToPlay() }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (!task.isSuccessful) {
                        Log.e(TAG, "signInWithCredential:failure", task.exception)
                        Snackbar.make(findViewById(R.id.container), "Failed to sign in", Snackbar.LENGTH_SHORT).show()
                    }
                }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)?.also {
                    firebaseAuthWithGoogle(it)
                }
            } catch (e: ApiException) {
                Log.e(TAG, e.message, e)
            }
        }
    }

    private fun testFunction() {
        API.testFunction(object : Then<String>() {
            override fun ok(data: String) {
                Snackbar.make(binding.container, data, Snackbar.LENGTH_SHORT).show()
            }

            override fun cancelled(reason: String) {
                Snackbar.make(binding.container, reason, Snackbar.LENGTH_SHORT).show()
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