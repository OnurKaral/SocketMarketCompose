package com.example.socketmarket.di

import com.example.socketmarket.data.csv.CSVParser
import com.example.socketmarket.data.csv.CompanyListingParser
import com.example.socketmarket.data.csv.IntraDayInfoParser
import com.example.socketmarket.data.repository.StockMarketRepositoryImpl
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.domain.model.IntraDayInfo
import com.example.socketmarket.domain.repository.StockMarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(parser: CompanyListingParser): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindCompanyListingRepository(stockMarketRepositoryImpl: StockMarketRepositoryImpl
    ): StockMarketRepository

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(parser: IntraDayInfoParser): CSVParser<IntraDayInfo>
}