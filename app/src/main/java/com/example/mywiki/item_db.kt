package com.example.mywiki

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract  class item_db: RoomDatabase() {

    abstract fun getItemDao(): ItemDao


    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: item_db? = null

        fun getDatabase(context: Context): item_db {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    item_db::class.java,
                    "item_db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }
}