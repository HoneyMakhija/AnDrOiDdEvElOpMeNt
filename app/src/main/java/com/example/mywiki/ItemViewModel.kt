package com.example.mywiki

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ItemViewModel(application: Application) :AndroidViewModel(application) {

    val allItems:LiveData<List<Item>>
    init {
        val dao=item_db.getDatabase(application).getItemDao()
        val repository=ItemRepository(dao)
        allItems=repository.allItems

    }
}