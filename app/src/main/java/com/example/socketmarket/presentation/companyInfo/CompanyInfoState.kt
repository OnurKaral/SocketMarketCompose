package com.example.socketmarket.presentation.companyInfo

import com.example.socketmarket.domain.model.CompanyInfo
import com.example.socketmarket.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfos: List<IntraDayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
