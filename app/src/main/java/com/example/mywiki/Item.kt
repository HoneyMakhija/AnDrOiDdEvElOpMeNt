package com.example.mywiki

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName= "item_table")
class Item(@ColumnInfo(name ="item")val tet:String)
{ @PrimaryKey(autoGenerate = true) var id=0
}


