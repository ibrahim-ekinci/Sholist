package com.gloorystudio.sholist.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.model.User

class UserListViewModel :ViewModel() {
    val users = MutableLiveData <List<User>>()

    fun refreshUserData(userList:List<User>){
        users.value=userList
    }
}