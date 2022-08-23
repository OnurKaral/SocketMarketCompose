package com.example.socketmarket.domain.repository

import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.domain.model.IntraDayInfo
import com.example.socketmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockMarketRepository {

    suspend fun getCompanyListings(
        fetchFromRemote:  Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}