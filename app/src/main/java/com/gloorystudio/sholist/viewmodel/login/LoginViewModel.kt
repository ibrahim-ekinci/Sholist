package com.gloorystudio.sholist.viewmodel.login

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.data.api.model.auth.LoginWithGoogle
import com.gloorystudio.sholist.data.api.model.auth.SignIn
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithJwt
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithJwtAndTt
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.setJwt
import com.gloorystudio.sholist.data.setUserData
import com.gloorystudio.sholist.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    fun initialAuth() {
        auth = Firebase.auth
    }


    fun loginWithGoogle(idToken: String, activity: FragmentActivity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    val user = auth.currentUser

                    user?.let { u->
                        loginWithGoogleApi(activity,u)
                    }
                    println(user.email)
                    println(user.displayName)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(activity, activity.getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginWithGoogleApi(activity: FragmentActivity, user: FirebaseUser?) {
        LoadingDialogShow(activity)

        disposable.add(
            apiService.loginWithGoogle(LoginWithGoogle(getDeviceId(),user!!.email,getStaticToken()))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<ApiResponseWithJwtAndTt>(){
                    override fun onSuccess(response: ApiResponseWithJwtAndTt) {
                        LoadingDialogCancel()

                        if (response.registered){
                            saveJwtAndUser(activity,response.jwt,response.member)
                            //TODO: MAİN FLOW A YÖNLENDİR.
                        }else{
                            //TODO:  kullanıcı isminin belirlenmemiş olduğunu belirtir
                            //YÖNLENDİR.
                        }
                    }

                    override fun onError(e: Throwable) {
                        LoadingDialogCancel()
                        Toast.makeText(activity, e.message, Toast.LENGTH_SHORT).show()
                    }

                })
        )
    }


    fun loginWithEmailAndPass(context: Context, email: String, password: String) {
        LoadingDialogShow(context)

        disposable.add(
            apiService.signIn(SignIn(getDeviceId(), email, password))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithJwt>() {
                    override fun onSuccess(response: ApiResponseWithJwt) {
                        LoadingDialogCancel()
                        if (response.state == "succes") {
                            saveJwtAndUser(context,response.jwt,response.member)
                            //TODO: MAİN FLOW A YÖNLENDİR.
                        }
                        else {
                            when(response.code){
                                402->{
                                    //TODO:  kullanıcı isminin belirlenmemiş olduğunu belirtir
                                    //YÖNLENDİR.
                                }
                                205->{
                                    //TODO:  kullanıcı email'ini onaylamadığını belirtir
                                    //YÖNLENDİR.
                                }
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        LoadingDialogCancel()
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

                    }

                })
        )


    }
    private fun getDeviceId():String{
        return ""//TODO RETURN DEVİCE ID
    }
    private fun getStaticToken():String{
        return ""//TODO: RETURNT STATİCID
    }

    private fun saveJwtAndUser(context: Context,jwt:String?,user: User?) {
        viewModelScope.launch {
            jwt?.let { j->
                setJwt(context, j)
            }
            user?.let { u->
                setUserData(context,u)
            }
        }
    }
}