package org.ph.expert.loan.ending.app.data.remote.dto.server_data


import com.google.gson.annotations.SerializedName

data class BaseDto(
    @SerializedName("app_config")
    val appConfigDto: AppConfigDto,
    @SerializedName("loans")
    val loanDtos: List<LoanDto>
)