package com.gloorystudio.sholist.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.R
import com.gloorystudio.sholist.getUserNames
import com.gloorystudio.sholist.model.Invitation
import com.gloorystudio.sholist.model.Item
import com.gloorystudio.sholist.model.ShoppingCard
import com.gloorystudio.sholist.model.User

class ShoppingCardViewModel:ViewModel() {
    val ShoppingCards = MutableLiveData<List<ShoppingCard>>()
    val ShoppingCardsError = MutableLiveData<Boolean>()
    val ShoppingCardsLoading = MutableLiveData<Boolean>()
    val ShoppingCardsIsEmpty =MutableLiveData<Boolean>()

    val Invitations = MutableLiveData<List<Invitation>>()
    val InvitationsError = MutableLiveData<Boolean>()
    val InvitationsLoading =MutableLiveData<Boolean>()
    val InvitationsIsEmpty =MutableLiveData<Boolean>()

    fun refreshShoppingCardsData(){
        var sList: ArrayList<ShoppingCard> = ArrayList<ShoppingCard>()
        var itemList: ArrayList<Item> = ArrayList<Item>()
        var userList: ArrayList<User> = ArrayList<User>()




        userList.clear()
        itemList.clear()
        sList.clear()

        userList.add(User("1","asd@asd.com.tr",true,"Halil İbrahim Ekinci","ibrahim","1",true))
        userList.add(User("2","asd@asd.com.tr",true,"Yunus Emre Bulut","yunusemre","2",true))
        userList.add(User("3","asd@asd.com.tr",true,"Hilal Tokgöz","hilal","3",false))
        userList.add(User("4","asd@asd.com.tr",true,"Recep Yeşikaya","recep","4",false))

        itemList.add(Item("1","Ekmek",2,true, R.drawable.ic_jam))
        itemList.add(Item("2","Elma",3,true, R.drawable.ic_jam))
        itemList.add(Item("3","Armut",1,true, R.drawable.ic_jam))
        itemList.add(Item("4","Muz",1,true, R.drawable.ic_jam))
        itemList.add(Item("5","Kivi",1,false, R.drawable.ic_jam))
        itemList.add(Item("6","Cips",1,false, R.drawable.ic_jam))
        itemList.add(Item("7","Kraker",1,false, R.drawable.ic_jam))
        itemList.add(Item("8","Selpak",1,false, R.drawable.ic_jam))
        itemList.add(Item("9","Su",2,false, R.drawable.ic_jam))
        itemList.add(Item("10","Kola",2,false, R.drawable.ic_jam))

        sList.add(ShoppingCard("1","My Shoping List","1",1,userList,itemList))
        sList.add(ShoppingCard("2","My Shoping List1","1",1,userList,itemList))
        sList.add(ShoppingCard("3","My Shoping List2","1",2,userList,itemList))
        sList.add(ShoppingCard("4","My Shoping List3","1",3,userList,itemList))
        sList.add(ShoppingCard("5","My Shoping List4","1",4,userList,itemList))

        ShoppingCards.value=sList
        ShoppingCardsError.value=false
        ShoppingCardsIsEmpty.value=sList.isEmpty()
        ShoppingCardsLoading.value=false
    }

    fun refreshInvitationsData(){
        var invitationList: ArrayList<Invitation> = ArrayList<Invitation>()
        invitationList.add(Invitation("1","","Alınacaklar","İbrahim","26.25.2020"))
        invitationList.add(Invitation("2","","Bim","Yunus Emre","26.25.2020"))
        invitationList.add(Invitation("3","","A-101","Recep","26.25.2020"))
        invitationList.add(Invitation("4","","Dükkan","Hilal","26.25.2020"))

        Invitations.value=invitationList
        InvitationsIsEmpty.value=invitationList.isEmpty()
    }
}