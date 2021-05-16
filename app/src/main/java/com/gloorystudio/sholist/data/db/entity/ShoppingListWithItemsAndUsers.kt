package com.gloorystudio.sholist.data.db.entity

import androidx.room.Embedded
import androidx.room.Relation


data class ShoppingListWithItemsAndUsers(
    @Embedded val shoppingList: ShoppingList,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val items: List<Item>,
    @Relation(
        parentColumn = "id",
        entityColumn = "shoppingListId"
    )
    val users: List<User>
)
