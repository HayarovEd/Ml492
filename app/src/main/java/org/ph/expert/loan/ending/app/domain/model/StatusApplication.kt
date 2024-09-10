package org.ph.expert.loan.ending.app.domain.model


sealed class StatusApplication {
    data object Welcome: StatusApplication()
    data object FindLoan: StatusApplication()
    //data object FinanceGuide: StatusApplication()
    data object FinanceMarket: StatusApplication()
    data object Terms: StatusApplication()
    //data object Privacy: StatusApplication()
    class Connect(val baseState: BaseState): StatusApplication()
    class Web (
        val url: String,
        val offerName: String
    ): StatusApplication()

    //class Offer(val loan: Loan): StatusApplication()

    data object Reconnect : StatusApplication()
}
