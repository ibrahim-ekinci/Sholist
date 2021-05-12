package com.gloorystudio.sholist.viewmodel.main

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.LoadingDialogShow
import com.gloorystudio.sholist.model.Item


class ListViewModel : ViewModel(){
    val items = MutableLiveData<List<Item>>()
    val itemsError = MutableLiveData<Boolean>()
    val itemsLoading =MutableLiveData<Boolean>()


    fun refreshItemListData(itemList: List<Item>){
        items.value=itemList

    }


}