package com.gloorystudio.sholist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.model.Invitation
import com.gloorystudio.sholist.model.ShoppingCard

class ShoppingCardViewModel:ViewModel() {
    val ShoppingCards = MutableLiveData<List<ShoppingCard>>()
    val Invitations = MutableLiveData<List<Invitation>>()

    fun refreshShoppingCardsData(shoppingCardList:List<ShoppingCard>){
        ShoppingCards.value=shoppingCardList
    }

    fun refreshInvitationsData(invitationList:List<Invitation>){
        Invitations.value=invitationList
    }
}