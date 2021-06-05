package com.gloorystudio.sholist.viewmodel.main

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.currentData
import com.gloorystudio.sholist.currentData.currentJwt
import com.gloorystudio.sholist.data.api.model.auth.ChangePassword
import com.gloorystudio.sholist.data.api.model.auth.SetNames
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.service.SholistApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class SettingsViewModel:ViewModel() {
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun changeName(context: Context,newName:String){
        currentJwt?.let {jwt->
            disposable.add(apiService.changeName(jwt,newName)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponse>(){
                    override fun onSuccess(t: ApiResponse) {
                        Toast.makeText(context, context.getString(R.string.name_has_been_changed), Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }

                })
            )
        }
    }

    fun changePassword(context: Context,newPassword: String,oldPassword:String){
        currentJwt?.let { jwt->
            disposable.add(apiService.changePassword(ChangePassword(jwt,newPassword,oldPassword))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponse>(){
                    override fun onSuccess(t: ApiResponse) {
                        Toast.makeText(context, context.getString(R.string.password_has_been_changed), Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    }
                })
            )
        }
    }
}