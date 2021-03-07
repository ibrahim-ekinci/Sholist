package com.gloorystudio.sholist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.model.ShoppingCard

class ShoppingCardViewModel:ViewModel() {
    val ShoppingCards = MutableLiveData<List<ShoppingCard>>()

    fun refreshShoppingCardsData(shoppingCardList:List<ShoppingCard>){
        ShoppingCards.value=shoppingCardList
    }
}