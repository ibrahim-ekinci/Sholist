package com.gloorystudio.sholist.viewmodel.login

import android.content.ContentValues
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel :ViewModel(){
    private lateinit var auth: FirebaseAuth

    fun initialAuth(){
        auth = Firebase.auth
    }
    

    fun firebaseAuthWithGoogle(idToken: String, activity:FragmentActivity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser
                    //updateUI(user)
                    //TODO:YÖNLENDİR GİRİŞ BAŞARILI
                    println(user.email)
                    println(user.displayName)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    //updateUI(null)
                    //TODO:GİRİŞ BAŞARISIZ UYAR
                }
            }
    }
}