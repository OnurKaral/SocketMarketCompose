package com.example.socketmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CompanyListingEntity::class], version = 1, exportSchema = false)
abstract class StockMarketDatabase: RoomDatabase() {
abstract fun dao(): StockMarketDao
}