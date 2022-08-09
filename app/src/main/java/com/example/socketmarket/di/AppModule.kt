package com.example.socketmarket.di

import android.app.Application
import androidx.room.Room
import com.example.socketmarket.data.local.StockMarketDatabase
import com.example.socketmarket.data.remote.StockMarketApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockMarketApi(): StockMarketApi {
        return Retrofit.Builder().baseUrl(StockMarketApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create()).build().create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockMarketDatabase {
        return Room.databaseBuilder(
            app,
            StockMarketDatabase::class.java,
            "stockdb.db"
        ).build()
    }
}
