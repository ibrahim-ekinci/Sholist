package com.gloorystudio.sholist.model

data class ShoppingCard (
            val id:String,
            val name:String,
            val creatorId:String,
            val color:Int,
            val userList:ArrayList<User>,
            val itemList:ArrayList<Item>
        ){


}