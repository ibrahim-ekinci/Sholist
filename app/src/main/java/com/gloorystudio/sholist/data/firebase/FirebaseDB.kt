package com.gloorystudio.sholist.data.firebase

import com.gloorystudio.sholist.currentData.currentUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

val database = Firebase.database

fun setListVersion(listId:String,version:Int){
    currentUser?.let {u->
        database.getReference("Lists").child(listId).child("v").setValue(version)
    }
}
fun setUserInvitation(username:String,listId: String){
    currentUser?.let { u->
        database.getReference("Invitations").child(username).setValue(listId)
    }
}


