package org.ph.expert.loan.ending.app.ui.state

import org.ph.expert.loan.ending.app.domain.model.StatusApplication


sealed class MainEvent {
    //data object ReconnectFirstLoad: MainEvent()
    data object Reconnect : MainEvent()
    data object SetNotFirstRun: MainEvent()
    class OnChangeStatusApplication(val statusApplication: StatusApplication): MainEvent()
    class UpdateLastStatusApplication(val statusApplication: StatusApplication): MainEvent()
    class OnGoToWeb(
        val urlOffer: String,
        val nameOffer: String
        ): MainEvent()
}
