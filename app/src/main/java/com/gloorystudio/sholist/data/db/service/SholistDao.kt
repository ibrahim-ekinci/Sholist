package com.gloorystudio.sholist.data.db.service

import androidx.room.*
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.ShoppingList
import com.gloorystudio.sholist.data.db.entity.ShoppingListWithItemsAndUsers
import com.gloorystudio.sholist.data.db.entity.User
/*
@Dao
interface SholistDao {
/*
    @Insert
    suspend fun insertShoppingCardAll(vararg shoppingCardEntity: ShoppingCardEntity):List<Long>

    @Query("SELECT * FROM shoppingcardentity")
    suspend fun getAllShoppingCards():List<ShoppingCardEntity>

    @Query("SELECT * FROM shoppingcardentity WHERE id=:id")
    suspend fun getShoppingCard(id:String):ShoppingCardEntity

    @Query("DELETE FROM shoppingcardentity WHERE id=:id")
    suspend fun deleteShoppingCard(id: String)

    @Query("DELETE FROM shoppingcardentity")
    suspend fun  deleteAllShoppingCard()

 */

    @Insert
    suspend fun insertAllShoppingList(list: List<ShoppingList>)
   /*
    @Query("DELETE FROM shoppinglist")
    suspend fun deleteAllShoppingList()
   */

    @Insert
    suspend fun insertShoppingList(shoppingList: ShoppingList)

    @Query("DELETE FROM shoppinglist WHERE id=:id")
    suspend fun deleteShoppingList(id:Int)

    @Query("SELECT * FROM user WHERE shoppingListId=:shoppingListId")
    suspend fun getAllUser(shoppingListId:Int):List<User>

    @Insert
    suspend fun insertAllUser(list: List<User>)

    @Query("DELETE FROM user WHERE shoppingListId=:shoppingListId")
    suspend fun deleteAllUser(shoppingListId:Int)

    @Insert
    suspend fun insertUser(user: User)

    @Query("DELETE FROM user WHERE id=:id")
    suspend fun deleteUser(id:Int)

    @Query("SELECT * FROM item")
    suspend fun getAllItems():List<Item>

    @Query("SELECT * FROM item WHERE shoppingListId=:shoppingListId")
    suspend fun getAllItems(shoppingListId:Int):List<Item>

    @Insert
    suspend fun insertAllItems(list: List<Item>)

    @Query("DELETE FROM item WHERE shoppingListId=:shoppingListId")
    suspend fun deleteAllItem(shoppingListId: Int)

    @Insert
    suspend fun insertItem(item: Item)

    @Query("DELETE FROM item WHERE id=:id")
    suspend fun deleteItem(id:Int)


    @Transaction
    suspend fun insertAllShoppingListWithItemsAndUsers(list: List<ShoppingListWithItemsAndUsers>) {
        for (item in list) {
            insertShoppingList(item.shoppingList)
            insertAllItems(item.items)
            insertAllUser(item.users)
        }
    }

    @Transaction
    suspend fun insertShoppingListWithItemsAndUsers(shoppingListWithItemsAndUsers: ShoppingListWithItemsAndUsers){
        insertShoppingList(shoppingListWithItemsAndUsers.shoppingList)
        insertAllItems(shoppingListWithItemsAndUsers.items)
        insertAllUser(shoppingListWithItemsAndUsers.users)
    }


    @Transaction
    @Query("SELECT * FROM shoppinglist")
    suspend fun getAllShoppingListWithItemsAndUsers(): List<ShoppingListWithItemsAndUsers>

    @Transaction
    @Query("SELECT * FROM shoppinglist WHERE id=:id")
    suspend fun getShoppingListWithItemsAndUsers(id: Int): ShoppingListWithItemsAndUsers

    @Transaction
    suspend fun deleteShoppnigListWithItemsAndUsers(id: Int){
        deleteShoppingList(id)
        deleteAllItem(id)
        deleteAllUser(id)
    }

    @Transaction
    suspend fun updateDefaulItemList(itemList:List<Item>){
        deleteAllItem(-1)
        insertAllItems(itemList)
    }

    @Query("DELETE FROM user")
    suspend fun clearAllUser()

    @Query("DELETE FROM item")
    suspend fun clearAllItem()

    @Query("DELETE FROM shoppinglist")
    suspend fun clearAllShoppingList()

    @Transaction
    suspend fun clearAll(){
        clearAllItem()
        clearAllUser()
        clearAllShoppingList()
    }

}

 */