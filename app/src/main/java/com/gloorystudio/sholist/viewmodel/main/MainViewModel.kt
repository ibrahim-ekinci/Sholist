package com.gloorystudio.sholist.viewmodel.main

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gloorystudio.sholist.*
import com.gloorystudio.sholist.CurrentData.currentJwt
import com.gloorystudio.sholist.CurrentData.currentUser
import com.gloorystudio.sholist.data.api.model.invitation.PatchInvitation
import com.gloorystudio.sholist.data.api.model.response.*
import com.gloorystudio.sholist.data.api.model.shoppingcard.PostShoppingCard
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.data.getItemVersion
import com.gloorystudio.sholist.data.logout
import com.gloorystudio.sholist.data.setItemVersion
import com.gloorystudio.sholist.model.DefItem
import com.gloorystudio.sholist.model.Invitation
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.view.login.LoginActivity
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
import retrofit2.HttpException
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : BaseViewModel(application) {
    val shoppingCards = MutableLiveData<List<ShoppingCard>>()
    val shoppingCardsError = MutableLiveData<Boolean>()
    val shoppingCardsLoading = MutableLiveData<Boolean>()
    val shoppingCardsIsEmpty = MutableLiveData<Boolean>()

    val invitations = MutableLiveData<List<Invitation>>()
    val invitationsError = MutableLiveData<Boolean>()
    val invitationsLoading = MutableLiveData<Boolean>()
    val invitationsIsEmpty = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()
    private val dbRef = Firebase.database.reference
    private var context: Context? = null
    private var activity: Activity? = null


    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    fun refreshShoppingCardsData(context: Context,activity: Activity) {
        var sList: ArrayList<ShoppingCard> = ArrayList<ShoppingCard>()
        var itemList: ArrayList<Item> = ArrayList<Item>()
        var userList: ArrayList<User> = ArrayList<User>()
        this.context = context
        checkDefaultItemList(context)
        userList.clear()
        itemList.clear()
        sList.clear()

        getAllData()
        shoppingCardsError.value = false
        shoppingCardsIsEmpty.value = sList.isEmpty()
        shoppingCardsLoading.value = false
    }

    private fun checkDefaultItemList(context: Context) {
        viewModelScope.launch {
            val localVersion = getItemVersion(context)
            if (localVersion != null && localVersion != "") {
                val version = localVersion.toInt()
                checkVersion(context, version)
            } else updateDefaultItems(1,context)
        }

    }

    private fun checkVersion(context: Context, version: Int) {
        currentJwt?.let { jwt ->
            disposable.add(
                apiService.getTemplateVersion(jwt)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ApiResponseWithVersion>() {
                        override fun onSuccess(response: ApiResponseWithVersion) {
                            if (version != response.version) {
                                updateDefaultItems(version,context)
                            }
                        }

                        override fun onError(e: Throwable) {
                            Toast.makeText(context, "checkVersion:"+ e.message, Toast.LENGTH_SHORT).show()
                        }

                    })
            )
        }

    }

    private fun updateDefaultItems(version: Int,context: Context) {
        currentJwt?.let { jwt ->
            disposable.add(apiService.getTemplateItems(jwt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ApiResponseWithItemList>() {
                    override fun onSuccess(response: ApiResponseWithItemList) {
                        updateLocalDefaultItems(context,version,response.items)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, "updateDefaultItems:"+e.message, Toast.LENGTH_SHORT).show()
                    }

                }
                )
            )
        }
    }

    private fun updateLocalDefaultItems(context: Context,version: Int, items: List<DefItem>) {
        viewModelScope.launch {
            items?.let {
                setItemVersion(context,version.toString())
            }

        }
    }

    private fun getAllInvitations(context: Context) {
        currentJwt?.let { jwt ->
            LoadingDialogShow(context)
            disposable.add(
                apiService.getInvitation(jwt)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ApiResponseWithInvitation>() {
                        override fun onSuccess(response: ApiResponseWithInvitation) {
                            LoadingDialogCancel()
                            invitations.value = response.requests
                            invitationsIsEmpty.value = response.requests.isEmpty()
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context,"getAllInvitations:"+ e.message, Toast.LENGTH_SHORT).show()
                            viewModelScope.launch {
                                logout(context)
                            }

                        }

                    })
            )
        }
    }

    fun createNewListWithApi(context: Context, name: String, color: Int, dialog: Dialog) {
        println("test3")
        currentJwt?.let { jwt ->
            println("JWT:$jwt")
            LoadingDialogShow(context)
            println("test2")
            disposable.add(
                apiService.postShoppingCard(PostShoppingCard(color, jwt, name))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                        override fun onSuccess(t: ApiResponseWithShoppingCard) {
                            LoadingDialogCancel()
                            //createNewListToSqlite(t.shoppingCard.toEntity().shoppingList, true)
                            dialog.cancel()
                            getAllData()
                            println("test1")
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()

                            Toast.makeText(context, "createNewListWithApi"+e.message, Toast.LENGTH_SHORT).show()
                            /*
                            currentUser?.let { user ->
                                val shoppingList = ShoppingList(
                                    UUID.randomUUID().toString(),
                                    name,
                                    user.id,
                                    color
                                )
                                //createNewListToSqlite(shoppingList,false)
                                //todo: internet yokken liste oluşturma yapılacak.
                            }

                             */
                        }

                    })
            )
        }
    }

    /*
    private fun createNewListToSqlite(shoppingList: ShoppingList, isSync: Boolean) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            shoppingList.isDbSync = isSync
            dao.insertShoppingList(shoppingList)
            readFromSQLite()
        }
    }

     */

    private fun getAllData() {
        getAllDataFromApi()
    }


    private fun getAllDataFromApi() {
        currentJwt?.let { jwt ->
            shoppingCardsLoading.value = true
            disposable.add(
                apiService.getShoppingCardAll(jwt)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCardList>() {
                        override fun onSuccess(response: ApiResponseWithShoppingCardList) {
                            //updateAllListInSQLite(response.shoppingCard)
                            shoppingCards.value=response.shoppingCards
                            shoppingCardsLoading.value = false
                            println("api succes")
                        }

                        override fun onError(e: Throwable) {
                            //readFromSQLite()
                            shoppingCardsLoading.value = false
                            if(e is HttpException){
                                when(e.code()){
                                    403->{
                                        activity?.let {
                                            val intent = Intent(it, LoginActivity::class.java)
                                            it.startActivity(intent)
                                            it.finish()
                                            logOut(it,it)
                                        }
                                    }
                                }
                            }
                        }

                    })
            )
        }
    }

    fun refreshInvitationsData(context: Context) {
        this.context = context
        addInvitationListener()
    }

/*
    private fun updateAllListInSQLite(sList: List<ShoppingCard>) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            sList?.let{
                for (shoppingCard in sList) {//Clear room
                    dao.deleteShoppnigListWithItemsAndUsers(shoppingCard.id)
                }
                dao.insertAllShoppingListWithItemsAndUsers(ArrayList(sList).toEntity())
                readFromSQLite()
            }
        }
    }

    private fun readFromSQLite() {
        launch {
            println("read data")
            val dao = SholistDatabase(getApplication()).sholistDao()
            ShoppingCards.value = dao.getAllShoppingListWithItemsAndUsers().toModel()

        }
    }
*/
    fun invitationAccept(context: Context, isAccept: Boolean, invation: Invitation) {
        currentJwt?.let { jwt ->
            LoadingDialogShow(context)
            disposable.add(
                apiService.patchInvitation(PatchInvitation(isAccept, invation.id, jwt))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableSingleObserver<ApiResponse>() {
                        override fun onSuccess(response: ApiResponse) {
                            LoadingDialogCancel()
                            getAllInvitations(context)
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context, "invitationAccept"+e.message, Toast.LENGTH_SHORT).show()
                        }

                    })

            )
        }
    }

    val invitationListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            context?.let { c ->
                getAllInvitations(c)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

    private fun addInvitationListener() {
        currentUser?.let { u ->
            dbRef.child("Invitations").child(u.username.toString())
                .addValueEventListener(invitationListener)
        }

    }

    fun removeInvitationListener() {
        currentUser?.let { u ->
            dbRef.child("Invitations").child(u.username.toString())
                .removeEventListener(invitationListener)
        }
    }

    fun logOut(context:Context,activity: Activity) {
        currentJwt?.let { jwt->
            disposable.add(apiService.signOut(jwt)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<ApiResponse>(){
                    override fun onSuccess(response: ApiResponse) {
                        viewModelScope.launch {
                            logout(context)
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        }
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(context, "logOut"+e.message, Toast.LENGTH_SHORT).show()
                        viewModelScope.launch {
                            logout(context)
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity(intent)
                            activity.finish()
                        }
                    }

                }))
        }
    }

}