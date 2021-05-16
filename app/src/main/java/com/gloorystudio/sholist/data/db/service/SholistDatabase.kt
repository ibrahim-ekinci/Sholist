package com.gloorystudio.sholist.data.db.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gloorystudio.sholist.data.db.entity.Item
import com.gloorystudio.sholist.data.db.entity.ShoppingList
import com.gloorystudio.sholist.data.db.entity.User


@Database(
    entities = [Item::class, ShoppingList::class, User::class],
    version = 1
)
abstract class SholistDatabase : RoomDatabase() {

    abstract fun sholistDao(): SholistDao

    //Singleton

    companion object {

        @Volatile
        private var instance: SholistDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: makeDatabase(context).also {
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            SholistDatabase::class.java,
            "sholistdatabase"
        ).build()
    }
}