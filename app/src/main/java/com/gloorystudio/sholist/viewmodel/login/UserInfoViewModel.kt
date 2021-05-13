package com.gloorystudio.sholist.viewmodel.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.data.api.model.auth.SetNames
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.service.SholistApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserInfoViewModel : ViewModel() {
    val usernameError = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    fun setName(context: Context,setNames: SetNames){
        LoadingDialogShow(context)

        disposable.add(apiService.setNames(setNames)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ApiResponse>(){
                override fun onSuccess(t: ApiResponse) {
                    LoadingDialogCancel()
                    //Todo mail onaylamaya yönlendir
                    //TODO: KULLANICI ADI MEVCUTSA UYAR usernameError
                }

                override fun onError(e: Throwable) {
                    LoadingDialogCancel()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        )
    }
}