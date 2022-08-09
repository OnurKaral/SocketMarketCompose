package com.example.socketmarket.data.mapper

import com.example.socketmarket.data.local.CompanyListingEntity
import com.example.socketmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing{
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange,
    )
}
fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity{
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange,
    )
}