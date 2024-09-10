package org.ph.expert.loan.ending.app.domain.model

sealed class BaseState{
    data object Loans: BaseState()
}