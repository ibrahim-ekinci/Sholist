package com.gloorystudio.sholist.viewmodel.main

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gloorystudio.sholist.CurrentData.currentJwt
import com.gloorystudio.sholist.LoadingDialogCancel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.data.api.model.Item.DeleteItem
import com.gloorystudio.sholist.data.api.model.Item.Items
import com.gloorystudio.sholist.data.api.model.Item.PatchItem
import com.gloorystudio.sholist.data.api.model.Item.PostItem
import com.gloorystudio.sholist.data.api.model.response.*
import com.gloorystudio.sholist.data.api.model.shoppingcard.DeleteShoppingCard
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.firebase.setListVersion
import com.gloorystudio.sholist.model.DefItem
import com.gloorystudio.sholist.model.ShoppingCard
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
import java.util.*


class ListViewModel(application: Application) : BaseViewModel(application) {
    val items = MutableLiveData<List<Item>>()
    val itemsError = MutableLiveData<Boolean>()
    val itemsLoading = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()

    private val dbRef = Firebase.database.reference
    var shoppingCard: ShoppingCard? = null
    var context: Context? = null
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    fun refreshItemListData(itemList: List<Item>, context: Context) {
        items.value = itemList
        shoppingCard?.let {
            this.context = context
            addListener(it.id)
        }
    }

    private fun updateDataFromApi(context: Context) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                LoadingDialogShow(context)
                disposable.add(apiService.getShoppingCard(jwt, shoppingCard.id)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                        override fun onSuccess(response: ApiResponseWithShoppingCard) {
                            //updateDataToSQLite(response.shoppingCard)
                            items.value = response.shoppingCard.itemList
                            LoadingDialogCancel()

                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                getApplication(),
                                "updateDataFromApi" + e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            LoadingDialogCancel()
                            // readFromSQLite(shoppingCard)
                        }
                    }
                    )
                )
            }
        }
    }
/*
    private fun updateDataToSQLite(shoppingCard: ShoppingCard) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            dao.deleteShoppingList(shoppingCard.id)
            dao.insertShoppingListWithItemsAndUsers(shoppingCard.toEntity())
            readFromSQLite(shoppingCard)
        }
    }

 */

    val listener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            context?.let { c ->
                updateDataFromApi(c)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    /*
        private fun readFromSQLite(sData: ShoppingCard) {
            launch {
                println("readData")
                val dao = SholistDatabase(getApplication()).sholistDao()
                val data = dao.getShoppingListWithItemsAndUsers(sData.id)

                items.value = data.items
                shoppingCard = data.toModel()
            }
        }


     */
    private fun addListener(id: Int) {
        dbRef.child("Lists").child(id.toString()).child("v").addValueEventListener(listener)
    }

    fun removeListener() {
        shoppingCard?.let {
            dbRef.child("Lists").child(it.id.toString()).child("v").removeEventListener(listener)
            println("removeListener")
        }
    }

    fun addItem(context: Context, itemName: String, count: Int, done: (isSucces: Boolean) -> Unit) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                LoadingDialogShow(context)
                val newList: ArrayList<Items> = arrayListOf()
                newList.add(Items(count, itemName, R.drawable.shop))
                disposable.add(
                    apiService.postItem(PostItem(newList, jwt, shoppingCard.id))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :
                            DisposableSingleObserver<ApiResponseWithShoppingCardAndItemList>() {
                            override fun onSuccess(t: ApiResponseWithShoppingCardAndItemList) {
                                LoadingDialogCancel()
                                //updateDataToSQLite(t.shoppingCard)
                                updateDataFromApi(context)
                                setListVersion(t.shoppingCard.id, UUID.randomUUID().toString())
                                done.invoke(true)
                            }

                            override fun onError(e: Throwable) {
                                done.invoke(false)
                                LoadingDialogCancel()
                                Toast.makeText(context, "addItem" + e.message, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                )
            }
        }
    }

    fun setIcon(context: Context, item: Item, icon: Int) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                //LoadingDialogShow(context)
                //TODO:SetIcon
            }
        }
    }

    fun editList(context: Context, name: String, color: Int) {
        currentJwt?.let { jwt ->
            shoppingCard?.let { shoppingCard ->
                //LoadingDialogShow(context)
                //TODO:EDİTLİST
            }
        }
    }

    fun deleteItem(context: Context, item: Item) {
        currentJwt?.let { jwt ->
            Log.d(TAG, "deleteItem: jwt:$jwt - itemid:${item.id}")
            disposable.addAll(
                apiService.deleteItem(DeleteItem(item.id, jwt))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ApiResponse>() {
                        override fun onSuccess(t: ApiResponse) {
                            viewModelScope.launch {
                                item.shoppingListId?.let {
                                    setListVersion(
                                        it,
                                        UUID.randomUUID().toString()
                                    )
                                }
                                updateDataFromApi(context)
                            }
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(
                                context,
                                "deleteItem fail message:" + e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
            )
        }
    }

    fun deleteList(context: Context, shoppingCardId: Int, done: (isSucces: Boolean) -> Unit) {
        currentJwt?.let { jwt ->
            disposable.add(apiService.deleteShoppigCard(DeleteShoppingCard(jwt, shoppingCardId))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                    override fun onSuccess(t: ApiResponseWithShoppingCard) {
                        done.invoke(true)
                    }

                    override fun onError(e: Throwable) {
                        done.invoke(false)
                        Toast.makeText(
                            context,
                            "deleteList fail message:" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            )

        }
    }

    fun updateItemChecked(requireContext: Context, item: Item, checked: Boolean ,done:(isSuccess: Boolean)->Unit) {
        currentJwt?.let { jwt ->
            disposable.add(apiService.patchItem(PatchItem(checked, item.id, jwt))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithItem>() {
                    override fun onSuccess(t: ApiResponseWithItem) {
                        done.invoke(true)
                        item.shoppingListId?.let {
                            done.invoke(true)
                            setListVersion(
                                it,
                                UUID.randomUUID().toString()
                            )
                        }
                    }

                    override fun onError(e: Throwable) {
                        done.invoke(false)
                        Toast.makeText(
                            context,
                            "updateItemChecked" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            )
        }
    }

    fun getTemplateItems(done: (itemList: List<DefItem>?) -> Unit) {
        currentJwt?.let { jwt ->
            disposable.add(apiService.getTemplateItems(jwt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithItemList>() {
                    override fun onSuccess(t: ApiResponseWithItemList) {
                        done.invoke(t.items)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(
                            context,
                            "getTemplateItems" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                })
            )

        }

    }
}


