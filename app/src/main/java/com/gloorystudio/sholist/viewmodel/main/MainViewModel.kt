package com.gloorystudio.sholist.viewmodel.main

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.gloorystudio.sholist.*
import com.gloorystudio.sholist.currentData.currentJwt
import com.gloorystudio.sholist.currentData.currentUser
import com.gloorystudio.sholist.data.api.model.invitation.PatchInvitation
import com.gloorystudio.sholist.data.api.model.response.ApiResponse
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithInvitation
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithShoppingCard
import com.gloorystudio.sholist.data.api.model.response.ApiResponseWithShoppingCardList
import com.gloorystudio.sholist.data.api.model.shoppingcard.PostShoppingCard
import com.gloorystudio.sholist.data.api.service.SholistApiService
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.ShoppingList
import com.gloorystudio.sholist.data.db.entity.User
import com.gloorystudio.sholist.data.db.service.SholistDatabase
import com.gloorystudio.sholist.model.Invitation
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
import kotlin.collections.ArrayList

class MainViewModel(application: Application) : BaseViewModel(application) {
    val ShoppingCards = MutableLiveData<List<ShoppingCard>>()
    val ShoppingCardsError = MutableLiveData<Boolean>()
    val ShoppingCardsLoading = MutableLiveData<Boolean>()
    val ShoppingCardsIsEmpty = MutableLiveData<Boolean>()

    val Invitations = MutableLiveData<List<Invitation>>()
    val InvitationsError = MutableLiveData<Boolean>()
    val InvitationsLoading = MutableLiveData<Boolean>()
    val InvitationsIsEmpty = MutableLiveData<Boolean>()

    private val apiService = SholistApiService()
    private val disposable = CompositeDisposable()
    private val dbRef = Firebase.database.reference
    var context:Context?=null

/*
    private lateinit var forRemoveListenershoppingLists: ArrayList<ShoppingCard>
    private val listener = object : ValueEventListener {
        var isFirstListening = true
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            println("getdata firebase$isFirstListening")
            if (isFirstListening.not()) getAllDataFromApi(false)
            isFirstListening = false
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }

 */

    override fun onCleared() {
        super.onCleared()
        disposable.clear()

        /*
        if (this::forRemoveListenershoppingLists.isInitialized){
            removeListener(forRemoveListenershoppingLists)
        }

         */

    }

    fun refreshShoppingCardsData(context: Context) {
        var sList: ArrayList<ShoppingCard> = ArrayList<ShoppingCard>()
        var itemList: ArrayList<Item> = ArrayList<Item>()
        var userList: ArrayList<User> = ArrayList<User>()
        this.context=context

        userList.clear()
        itemList.clear()
        sList.clear()

        userList.add(
            User(
                "1",
                "1",
                "asd@asd.com.tr",
                true,
                "Halil İbrahim Ekinci",
                "ibrahim",
                "1",
                true
            )
        )
        userList.add(
            User(
                "2",
                "1",
                "asd@asd.com.tr",
                true,
                "Yunus Emre Bulut",
                "yunusemre",
                "2",
                true
            )
        )
        userList.add(User("3", "1", "asd@asd.com.tr", true, "Hilal Tokgöz", "hilal", "3", false))
        userList.add(User("4", "1", "asd@asd.com.tr", true, "Recep Yeşikaya", "recep", "4", false))

        itemList.add(Item("1", "1", "Ekmek", 2, true, R.drawable.ic_jam))
        itemList.add(Item("2", "1", "Elma", 3, true, R.drawable.ic_jam))
        itemList.add(Item("3", "1", "Armut", 1, true, R.drawable.ic_jam))
        itemList.add(Item("4", "1", "Muz", 1, true, R.drawable.ic_jam))
        itemList.add(Item("5", "1", "Cips", 1, false, R.drawable.ic_jam))
        itemList.add(Item("7", "1", "Kraker", 1, false, R.drawable.ic_jam))
        itemList.add(Item("8", "1", "Selpak", 1, false, R.drawable.ic_jam))
        itemList.add(Item("9", "1", "Su", 2, false, R.drawable.ic_jam))
        itemList.add(Item("10", "1", "Kola", 2, false, R.drawable.ic_jam))

        sList.add(ShoppingCard("1", "My Shoping List", "1", 1, userList, itemList))
        //   sList.add(ShoppingCard("2", "My Shoping List1", "1", 1, userList, itemList))
        //  sList.add(ShoppingCard("3", "My Shoping List2", "1", 2, userList, itemList))
        //  sList.add(ShoppingCard("4", "My Shoping List3", "1", 3, userList, itemList))
        //  sList.add(ShoppingCard("5", "My Shoping List4", "1", 4, userList, itemList))

        getAllData()
        //updateAllListInSQLite(sList)
        //readFromSQLite(true)
        ShoppingCardsError.value = false
        ShoppingCardsIsEmpty.value = sList.isEmpty()
        ShoppingCardsLoading.value = false
    }

    private fun getAllInvitations(context: Context){
        currentJwt?.let { jwt->
            LoadingDialogShow(context)
            disposable.add(
                apiService.getInvitation(jwt)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :DisposableSingleObserver<ApiResponseWithInvitation>(){
                        override fun onSuccess(response: ApiResponseWithInvitation) {
                            LoadingDialogCancel()
                            Invitations.value=response.requests
                            InvitationsIsEmpty.value = response.requests.isEmpty()
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }

                    })
            )
        }
    }

    fun createNewListWithApi(context: Context, name: String, color: Int, dialog: Dialog) {
        currentJwt?.let { jwt ->
            LoadingDialogShow(context)
            disposable.add(
                apiService.postShoppingCard(PostShoppingCard(color, jwt, name))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCard>() {
                        override fun onSuccess(t: ApiResponseWithShoppingCard) {
                            LoadingDialogCancel()
                            createNewListToSqlite(t.shoppingCard.toEntity().shoppingList, true)
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                                currentUser?.let { user ->
                                val shoppingList=ShoppingList(
                                    UUID.randomUUID().toString(),
                                    name,
                                    user.id,
                                    color
                                )
                                //createNewListToSqlite(shoppingList,false)
                                    //todo: internet yokken liste oluşturma yapılacak.
                            }
                        }

                    })
            )
        }
    }

    private fun createNewListToSqlite(shoppingList: ShoppingList, isSync: Boolean) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            shoppingList.isDbSync = isSync
            dao.insertShoppingList(shoppingList)
            readFromSQLite()
        }
    }

    private fun getAllData() {
        getAllDataFromApi()
    }


    private fun getAllDataFromApi() {
        currentJwt?.let { jwt ->
            ShoppingCardsLoading.value = true
            disposable.add(
                apiService.getShoppingCardAll(jwt)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :
                        DisposableSingleObserver<ApiResponseWithShoppingCardList>() {
                        override fun onSuccess(response: ApiResponseWithShoppingCardList) {
                            updateAllListInSQLite(response.shoppingCard)
                            ShoppingCardsLoading.value = false
                            println("api succes")
                        }

                        override fun onError(e: Throwable) {
                            readFromSQLite()
                            ShoppingCardsLoading.value = false
                            println("api fail")
                        }

                    })
            )
        }
    }

    fun refreshInvitationsData(context: Context) {
        var invitationList: ArrayList<Invitation> = ArrayList<Invitation>()
        invitationList.add(Invitation("1", "", "Alınacaklar", "İbrahim", "26.25.2020"))
        invitationList.add(Invitation("2", "", "Bim", "Yunus Emre", "26.25.2020"))
        invitationList.add(Invitation("3", "", "A-101", "Recep", "26.25.2020"))
        invitationList.add(Invitation("4", "", "Dükkan", "Hilal", "26.25.2020"))
        this.context=context
        addInvitationListener()
        Invitations.value = invitationList
    }


    private fun updateAllListInSQLite(sList: List<ShoppingCard>) {
        launch {
            val dao = SholistDatabase(getApplication()).sholistDao()
            for (shoppingCard in sList) {//Clear room
                dao.deleteShoppnigListWithItemsAndUsers(shoppingCard.id)
            }
            dao.insertAllShoppingListWithItemsAndUsers(ArrayList(sList).toEntity())

            readFromSQLite()
        }
    }

    private fun readFromSQLite() {
        launch {
            println("read data")
            val dao = SholistDatabase(getApplication()).sholistDao()
            ShoppingCards.value = dao.getAllShoppingListWithItemsAndUsers().toModel()

        }
    }

    fun invitationAccept(context: Context, isAccept: Boolean, invation: Invitation) {
        currentJwt?.let { jwt->
            LoadingDialogShow(context)
            disposable.add(
                apiService.patchInvitation(PatchInvitation(isAccept,invation.id,jwt))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object :DisposableSingleObserver<ApiResponse>(){
                        override fun onSuccess(response: ApiResponse) {
                            LoadingDialogCancel()
                           getAllInvitations(context)
                        }

                        override fun onError(e: Throwable) {
                            LoadingDialogCancel()
                            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }

                    })

            )
        }
    }
    val invitationListener = object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            context?.let { c->
               getAllInvitations(c)
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {}
    }
    private fun addInvitationListener(){
        currentUser?.let {u->
            dbRef.child("Invitations").child(u.username.toString()).addValueEventListener(invitationListener)
        }

    }
    fun removeInvitationListener(){
        currentUser?.let {u->
            dbRef.child("Invitations").child(u.username.toString()).removeEventListener(invitationListener)
        }
    }

/*
    private fun listenVersion(shoppingLists: ArrayList<ShoppingCard>) {
        val dbRef = Firebase.database.reference
        for (shoppingList in shoppingLists) {
            dbRef.child("Lists").child(shoppingList.id).child("v").addValueEventListener(listener)
            println("add listener")
        }

    }
    private fun removeListener(shoppingLists: ArrayList<ShoppingCard>){
        val dbRef = Firebase.database.reference
        for (shoppingList in shoppingLists) {
            dbRef.child("Lists").child(shoppingList.id).child("v").removeEventListener(listener)
            println("removed listener ")
        }
        forRemoveListenershoppingLists=shoppingLists
    }


 */


}