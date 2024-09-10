package org.ph.expert.loan.ending.app.domain.model


data class Loan(
    val description: String,
    val hidePercentFields: String,
    val hideTermFields: String,
    val id: Int,
    val itemId: String,
    val name: String,
    val order: String,
    val orderButtonText: String,
    val position: Int,
    val score: String,
    val screen: String,
    val showCash: String,
    val showMastercard: String,
    val showMir: String,
    val showQiwi: String,
    val showVisa: String,
    val showYandex: String,
    val amounts: String,
    val percent: String,
    val term: String,
)
