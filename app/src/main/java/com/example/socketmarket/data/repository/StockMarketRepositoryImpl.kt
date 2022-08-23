package com.example.socketmarket.data.repository

import com.example.socketmarket.data.csv.CSVParser
import com.example.socketmarket.data.csv.IntraDayInfoParser
import com.example.socketmarket.data.local.StockMarketDatabase
import com.example.socketmarket.data.mapper.toCompanyInfo
import com.example.socketmarket.data.mapper.toCompanyListing
import com.example.socketmarket.data.mapper.toCompanyListingEntity
import com.example.socketmarket.data.remote.StockMarketApi
import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.CompanyListing
import com.example.socketmarket.domain.model.IntraDayInfo
import com.example.socketmarket.domain.repository.StockMarketRepository
import com.example.socketmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockMarketRepositoryImpl @Inject constructor (
    private val api: StockMarketApi,
    private val db: StockMarketDatabase,
    val companyListingParser: CSVParser<CompanyListing>,
    val intraDayInfoParser: CSVParser<IntraDayInfo>

): StockMarketRepository {
    private val dao = db.dao()

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val listings = dao.searchCompanyListings(query)
            emit(Resource.Success(
                listings.map {
                    it.toCompanyListing()
                }
            ))
            val isDBEmpty = listings.isEmpty() && query.isBlank()
            val shouldFetchFromCache = !isDBEmpty && !fetchFromRemote
            if(shouldFetchFromCache) {
                emit(Resource.Loading(false))
            return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            }catch (e: IOException) {
                emit(Resource.Error("Error fetching listings from remote"))
                null
            }catch (e: HttpException) {
                emit(Resource.Error("Error fetching listings from remote"))
                null
            }
            remoteListings?.let {listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map {
                        it.toCompanyListingEntity()
                    })
                emit(Resource.Success(
                    dao.searchCompanyListings("").map {
                        it.toCompanyListing()
                    }
                ))
                emit(Resource.Loading(false))


            }
        }
    }

    override suspend fun getIntraDayInfo(symbol: String): Resource<List<IntraDayInfo>> {
        return try {
            val response = api.getIntraDayInfo(symbol)
            val result = intraDayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        }catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Error fetching intra day info")
        }catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Error fetching intra day info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = api.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        }catch (e: IOException) {
            e.printStackTrace()
            Resource.Error("Error fetching intra day info")
        }catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error("Error fetching intra day info")
        }
    }
}