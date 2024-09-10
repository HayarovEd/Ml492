package org.ph.expert.loan.ending.app.ui.state

import org.ph.expert.loan.ending.app.domain.model.BaseData
import org.ph.expert.loan.ending.app.domain.model.StatusApplication


data class MainState(
    val instanceIdMyTracker: String? = null,
    val hmsToken: String? = null,
    val gaid: String? = null,
    val message: String = "",
    val affsub1Unswer: String = "",
    val affsub2Unswer: String = "",
    val affsub2UnswerAF: String = "",
    val affsub2UnswerMT: String = "",
    val affsub2UnswerEmpty: String = "",
    val affsub3Unswer: String = "",
    val affsub5Unswer: String = "",
    val dbData: BaseData? = null,
    val isLoading:Boolean = false,
    val lastState: StatusApplication = StatusApplication.Welcome,
    val statusApplication: StatusApplication = StatusApplication.Welcome/*Web(url = "https://ya.ru/", offerName = "Offer")*/,
)
