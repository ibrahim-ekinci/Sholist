package com.gloorystudio.sholist.viewmodel.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.*
import com.gloorystudio.sholist.data.api.model.auth.SignUp
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithTt
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.api.*
import com.gloorystudio.sholist.databinding.FragmentRegisterBinding
import com.gloorystudio.sholist.view.login.RegisterFragmentDirections
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RegisterViewModel : ViewModel() {
    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun signUp(binding: FragmentRegisterBinding, context: Context, email: String, pass: String) {
        LoadingDialogShow(context)

        disposable.add(
            apiService.signUp(SignUp(email, pass))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithTt>() {
                    override fun onSuccess(response: ApiResponseWithTt) {
                        LoadingDialogCancel()
                        //TODO: Kullanıcı adı belirlemeye yönlendir.
                        RegisterFragmentDirections.actionRegisterFragmentToUserInfoFragment(
                            "",
                            response.tempToken!!,
                            email
                        ).go(binding.btnRegister)
                    }

                    override fun onError(e: Throwable) {
                        LoadingDialogCancel()
                        e.isHttpExc { code ->
                            when (code) {
                                ErrorCode.EXIST_DATA_422->{
                                    binding.textInputLayoutEmail.error=context.getString(R.string.email_allready_registred)
                                }
                                ErrorCode.INVALID_JSON_400 -> ""
                                ErrorCode.INVALID_PERMISSION_403 -> ""
                                ErrorCode.SERVER_ERROR_500 -> ""
                                else -> Toast.makeText(context, e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }

                })
        )
    }
}