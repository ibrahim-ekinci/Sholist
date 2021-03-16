package com.gloorystudio.sholist.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.model.Item


class ListViewModel : ViewModel(){
    val items = MutableLiveData<List<Item>>()

    fun refreshItemListData(itemList: List<Item>){
        items.value=itemList
    }


}