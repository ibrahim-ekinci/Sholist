package com.gloorystudio.sholist.viewmodel.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gloorystudio.sholist.data.db.entity.User

class UserListViewModel :ViewModel() {
    val users = MutableLiveData <List<User>>()
    val usersError=MutableLiveData<Boolean>()
    val usersLoading =MutableLiveData<Boolean>()

    fun refreshUserData(userList:List<User>){
        users.value=userList
    }
}