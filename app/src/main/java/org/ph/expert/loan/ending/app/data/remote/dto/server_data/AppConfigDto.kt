package org.ph.expert.loan.ending.app.data.remote.dto.server_data


import com.google.gson.annotations.SerializedName

data class AppConfigDto(
    @SerializedName("extra_field_0")
    val extraField0: String,
    @SerializedName("extra_field_1")
    val extraField1: String,
    @SerializedName("privacy_policy_html")
    val privacyPolicyHtml: String,
    @SerializedName("show_calculator_item")
    val showCalculatorItem: String,
    @SerializedName("show_chat")
    val showChat: String,
    @SerializedName("show_news_item")
    val showNewsItem: String,
    @SerializedName("show_phone_authentication")
    val showPhoneAuthentication: String,
    @SerializedName("user_term_html")
    val userTermHtml: String
)