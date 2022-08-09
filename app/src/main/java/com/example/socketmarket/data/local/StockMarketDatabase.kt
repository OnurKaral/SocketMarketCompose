package com.example.socketmarket.data.local

import androidx.room.Database

@Database(entities = [CompanyListingEntity::class], version = 1)
abstract class StockMarketDatabase {
abstract fun dao(): StockMarketDao
}