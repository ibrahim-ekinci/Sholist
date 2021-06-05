package com.gloorystudio.sholist.data.firebase

import com.gloorystudio.sholist.currentData.currentUser
import com.gloorystudio.sholist.encrypt
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val database = Firebase.database

fun setListVersion(listId:String,version:String){
    currentUser?.let {u->
        database.getReference("Lists").child(listId).child("v").setValue(version)
    }
}
fun setUserInvitation(username:String,listId: String){
    currentUser?.let { u->
        database.getReference("Invitations").child(username).setValue(listId)
    }
}

fun getStaticToken():String{
    val pw ="OyR&Sb43g4ZJs@do"
    val db = Firebase.firestore
    var token="Clwx1Az9LxR7ScKXBcXaKMq3PnTjUtrneyU048gYxQU="
    db.collection("statictoken").document("token").get().addOnSuccessListener { result->
        val tempToken = result.getString("token").toString()
        token = tempToken.encrypt(pw)
    }
    return token
}


