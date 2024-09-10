package org.ph.expert.loan.ending.app.domain.model


data class BaseData(
    val appConfig: AppConfig,
    val loans: List<Loan>
)
