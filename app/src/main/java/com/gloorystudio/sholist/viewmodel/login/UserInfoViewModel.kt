package com.gloorystudio.sholist.viewmodel.login

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.go
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.data.api.model.auth.SetNames
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.view.login.UserInfoFragmentDirections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class UserInfoViewModel : ViewModel() {
    val usernameError = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
    fun setName(view: View, context: Context, setNames: SetNames){
        LoadingDialogShow(context)

        disposable.add(apiService.setNames(setNames)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ApiResponse>(){
                override fun onSuccess(t: ApiResponse) {
                    LoadingDialogCancel()
                    //TODO: KULLANICI ADI MEVCUTSA UYAR usernameError
                    UserInfoFragmentDirections.actionUserInfoFragmentToVerificationFragment(setNames.email)
                        .go(view)
                }

                override fun onError(e: Throwable) {
                    LoadingDialogCancel()
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    if(e is HttpException){
                        when(e.code()){
                            401->" "
                        }
                    }
                }
            })
        )
    }
}