package com.gloorystudio.sholist.viewmodel.main

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.currentData.currentJwt
import com.gloorystudio.sholist.data.api.model.auth.SignOut
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithShoppingCard
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.service.SholistDatabase
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.toEntity
import com.gloorystudio.sholist.toModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch


class ListViewModel(application: Application) : BaseViewModel(application) {
    val items = MutableLiveData<List<Item>>()
    val itemsError = MutableLiveData<Boolean>()
    val itemsLoading = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    private val dbRef = Firebase.database.reference
    var shoppingCard: ShoppingCard? = null
    var context:Context?=null
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun refreshItemListData(itemList: List<Item>, context: Context) {
        items.value = itemList
        shoppingCard?.let {
            this.context=context
            addListener(it.id)
        }
    }

    private fun updateDataFromApi(context: Context) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                LoadingDialogShow(context)
                println("updatefROM APİ")
                disposable.add(apiService.getShoppingCard(jwt, shoppingCard.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                        override fun onSuccess(response: ApiResponseWithShoppingCard) {
                            updateDataToSQLite(response.shoppingCard)
                            LoadingDialogCancel()

                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(getApplication(), e.message, Toast.LENGTH_SHORT).show()
                            LoadingDialogCancel()
                            readFromSQLite(shoppingCard)
                        }
                    }
                    )
                )
            }
        }

    }

    private fun updateDataToSQLite(shoppingCard: ShoppingCard) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            dao.deleteShoppingList(shoppingCard.id)
            dao.insertShoppingListWithItemsAndUsers(shoppingCard.toEntity())
            readFromSQLite(shoppingCard)
        }
    }

    val listener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            context?.let { c->
                updateDataFromApi(c)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    private fun readFromSQLite(sData: ShoppingCard) {
        launch {
            println("readData")
            val dao = SholistDatabase(getApplication()).sholistDao()
            val data = dao.getShoppingListWithItemsAndUsers(sData.id)
            items.value = data.items
            shoppingCard = data.toModel()
        }
    }

    private fun addListener(id: String) {

        dbRef.child("Lists").child(id).child("v").addValueEventListener(listener)

    }

    fun removeListener() {
        shoppingCard?.let {
            dbRef.child("Lists").child(it.id).child("v").removeEventListener(listener)
            println("removeListener")
        }
    }
}


