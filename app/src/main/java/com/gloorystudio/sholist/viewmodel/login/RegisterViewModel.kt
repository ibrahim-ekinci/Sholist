package com.gloorystudio.sholist.viewmodel.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.data.api.model.auth.SignUp
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithTt
import com.gloorystudio.sholist.data.api.service.SholistApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : ViewModel() {
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    fun signUp(context: Context, email: String, pass: String) {
        LoadingDialogShow(context)

        disposable.add(
            apiService.signUp(SignUp(email, pass))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithTt>() {
                    override fun onSuccess(response: ApiResponseWithTt) {
                        LoadingDialogCancel()
                        //TODO: Kullanıcı adı belirlemeye yönlendir.
                    }

                    override fun onError(e: Throwable) {
                        LoadingDialogCancel()
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                })
        )
    }
}