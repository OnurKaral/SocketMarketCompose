package com.example.socketmarket.presentation.companyListing

import com.example.socketmarket.domain.model.CompanyListing
import retrofit2.http.Query

data class CompanyListingState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)