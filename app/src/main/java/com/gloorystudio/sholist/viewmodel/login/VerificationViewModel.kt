package com.gloorystudio.sholist.viewmodel.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.service.SholistApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class VerificationViewModel : ViewModel() {
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    fun sendMail(context: Context,email: String) {
        disposable.add(apiService.verifyEmail(email)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ApiResponse>() {
                override fun onSuccess(t: ApiResponse) {
                    LoadingDialogCancel()
                    Toast.makeText(context, context.getString(R.string.mail_hass_been_sent), Toast.LENGTH_SHORT).show()
                }

                override fun onError(e: Throwable) {
                    LoadingDialogCancel()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }

            }
            )
        )
    }
}