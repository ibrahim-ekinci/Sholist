package com.gloorystudio.sholist.viewmodel.main

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.CurrentData.currentJwt
import com.gloorystudio.sholist.data.api.model.invitation.DeleteInvitation
import com.gloorystudio.sholist.data.api.model.invitation.PostInvitation
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithShoppingCard
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.data.firebase.setUserInvitation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UserListViewModel(application: Application) : BaseViewModel(application) {
    val users = MutableLiveData<List<User>>()
    val usersError = MutableLiveData<Boolean>()
    val usersLoading = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    private var shoppingCard: ShoppingCard? = null

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun refreshUserData(shoppingCard: ShoppingCard, context: Context) {
        users.value = shoppingCard.userList
        this.shoppingCard = shoppingCard
        getUserDataFromApi(context)
    }

    private fun getUserDataFromApi(context: Context) {
        shoppingCard?.let { shoppingCard ->
            currentJwt?.let { jwt ->
                LoadingDialogShow(context)
                disposable.add(
                    apiService.getShoppingCard(jwt, shoppingCard.id)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :
                            DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                            override fun onSuccess(response: ApiResponseWithShoppingCard) {
                                LoadingDialogCancel()
                               // updateDataToSqlite(response.shoppingCard)
                                users.value =response.shoppingCard.userList
                            }

                            override fun onError(e: Throwable) {
                                LoadingDialogCancel()
                                //readUserDataFromSqlite(shoppingCard)
                            }

                        })
                )
            }
        }
    }
/*
    private fun updateDataToSqlite(shoppingCard: ShoppingCard) {
        viewModelScope.launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            dao.deleteAllUser(shoppingCard.id)
            dao.insertAllUser(shoppingCard.userList)
            readUserDataFromSqlite(shoppingCard)
        }
    }

    private fun readUserDataFromSqlite(shoppingCard: ShoppingCard) {
        viewModelScope.launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            users.value = dao.getAllUser(shoppingCard.id)
        }
    }

 */

    fun addNewUser(context: Context, username: String, dialog: Dialog) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                LoadingDialogShow(context)
                disposable.add(
                    apiService.postInvitation(PostInvitation(jwt, shoppingCard.id, username))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :
                            DisposableSingleObserver<ApiResponse>() {
                            override fun onSuccess(t: ApiResponse) {
                                Toast.makeText(context, context.getString(R.string.the_invitation_has_been_sent), Toast.LENGTH_SHORT).show()
                                dialog.cancel()
                                LoadingDialogCancel()
                                getUserDataFromApi(context)
                                setUserInvitation(username,shoppingCard.id)//firebase datasını güncelle
                            }

                            override fun onError(e: Throwable) {
                               LoadingDialogCancel()
                                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                                //TODO: Kullanıcı adı yoksa kontrol et uyar.
                            }

                        })
                )
            }
        }
    }
    fun removeUser(context: Context,user: User){
        shoppingCard?.let { shoppingCard->
            currentJwt?.let { jwt->
                LoadingDialogShow(context)
                disposable.add(apiService.deleteInvitation(DeleteInvitation(jwt,shoppingCard.id,user.id))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :DisposableSingleObserver<ApiResponse>(){
                        override fun onSuccess(t: ApiResponse) {
                            LoadingDialogCancel()
                            getUserDataFromApi(context)
                            Toast.makeText(context, context.getString(R.string.user_removed), Toast.LENGTH_SHORT).show()
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }

                    })
                )
            }
        }
    }


}