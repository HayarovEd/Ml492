package org.ph.expert.loan.ending.app.ui.state


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.ph.expert.loan.ending.app.domain.model.BaseState
import org.ph.expert.loan.ending.app.domain.model.StatusApplication
import org.ph.expert.loan.ending.app.domain.repository.RepositoryAnalytic
import org.ph.expert.loan.ending.app.domain.repository.RepositoryServer
import org.ph.expert.loan.ending.app.domain.repository.Service
import org.ph.expert.loan.ending.app.domain.repository.SharedKeeper
import org.ph.expert.loan.ending.app.domain.utils.APP_METRICA
import org.ph.expert.loan.ending.app.domain.utils.APY_KEY
import org.ph.expert.loan.ending.app.domain.utils.BACKEND_UNAVAILABLE
import org.ph.expert.loan.ending.app.domain.utils.EXTERNAL_LINK
import org.ph.expert.loan.ending.app.domain.utils.ITEM_ID
import org.ph.expert.loan.ending.app.domain.utils.LOANS
import org.ph.expert.loan.ending.app.domain.utils.MORE_DETAILS
import org.ph.expert.loan.ending.app.domain.utils.OFFER_WALL
import org.ph.expert.loan.ending.app.domain.utils.REQUEST_DB
import org.ph.expert.loan.ending.app.domain.utils.Resource
import org.ph.expert.loan.ending.app.domain.utils.URL
import org.ph.expert.loan.ending.app.domain.utils.setStatusByPush
import org.ph.expert.loan.ending.app.ui.state.MainEvent.OnChangeStatusApplication
import org.ph.expert.loan.ending.app.ui.state.MainEvent.OnGoToWeb
import org.ph.expert.loan.ending.app.ui.state.MainEvent.Reconnect
import org.ph.expert.loan.ending.app.ui.state.MainEvent.UpdateLastStatusApplication
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val service: Service,
    private val sharedKeeper: SharedKeeper,
    private val repositoryAnalytic: RepositoryAnalytic,
    private val repositoryServer: RepositoryServer,
) : ViewModel() {
    private var _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    private val _myTracker = MutableStateFlow("")
    private val _appsFlayer = MutableStateFlow("")
    private val _link = MutableStateFlow("")
    private val _yandexMetrikaDeviceId = MutableStateFlow("")
    private val _appsFlayerInstanceId = MutableStateFlow("")
    //private var _lastState = MutableStateFlow<StatusApplication>(StatusApplication.Welcome)

    init {
        viewModelScope.launch {
            loadData()
        }
    }


    fun loadAFDeeplink(deeplink: String) {
        Log.d("ASDFGH", "appsFlayer deeplink -  $deeplink")
        _appsFlayer.value = deeplink
        Log.d("ASDFGH", "appsFlayer start -  ${_appsFlayer.value}")

    }

    fun loadMTDeeplink(deeplink: String) {
        _myTracker.value = deeplink
    }

    fun loadLink(link: String) {
        _link.value = link
    }

    private suspend fun loadData() {
        _state.value.copy(
            statusApplication = StatusApplication.Welcome
        )
            .updateStateUI()
        val sharedYandexMetrica = sharedKeeper.getYandexMetricaDeviceId()
        if (sharedYandexMetrica.isNullOrBlank()) {
            service.getYandexMetricaDeviceId {
                _yandexMetrikaDeviceId.value = it ?: ""
                viewModelScope.launch {
                    sharedKeeper.setYandexMetricaDeviceId(it ?: "")
                }
            }
        } else {
            _yandexMetrikaDeviceId.value = sharedYandexMetrica
        }
        val instanceIdMyTracker =
            if (sharedKeeper.getMyTrackerInstanceId().isNullOrBlank()) {
                val instance = service.instanceIdMyTracker
                sharedKeeper.setMyTrackerInstanceId(instance)
                instance
            } else {
                sharedKeeper.getMyTrackerInstanceId()
            }
        val sharedFireBaseToken = sharedKeeper.getFireBaseToken()
        viewModelScope.launch(Dispatchers.IO) {
            if (sharedFireBaseToken.isNullOrBlank()) {
                viewModelScope.launch(Dispatchers.IO)
                {
                    service.getHmsToken().let { token ->
                        Log.d("PushDemoLog", "result request fb token? $token")
                        _state.value.copy(
                            hmsToken = token
                        )
                            .updateStateUI()
                        sharedKeeper.setFireBaseToken(token ?: "")
                    }
                    getSub3()
                }
            } else {
                _state.value.copy(
                    hmsToken = sharedFireBaseToken
                )
                    .updateStateUI()
                getSub3()
            }
            _appsFlayerInstanceId.value = sharedKeeper.getAppsFlyerInstanceId() ?: ""
            _state.value.copy(
                instanceIdMyTracker = instanceIdMyTracker
            )
                .updateStateUI()
            viewModelScope.launch(Dispatchers.IO) {
                val gaid = service.getOAID()
                _state.value.copy(
                    gaid = gaid,
                )
                    .updateStateUI()
                delay(2000)
                getSub1()
            }

            if (sharedKeeper.getSub2().isNullOrBlank()) {
                getFirstSub2()
            }

            getSub5()
            loadDbData()
        }
    }

    private fun MainState.updateStateUI() {
        _state.update {
            this
        }
    }

    fun onEvent(mainEvent: MainEvent) {
        when (mainEvent) {
            is OnChangeStatusApplication -> {
                _state.value.copy(
                    lastState = state.value.statusApplication,
                )
                    .updateStateUI()
                _state.value.copy(
                    statusApplication = mainEvent.statusApplication,
                )
                    .updateStateUI()
            }

            is OnGoToWeb -> {
                _state.value.copy(
                    lastState = _state.value.statusApplication
                )
                    .updateStateUI()

                viewModelScope.launch {
                    delay(2000)
                    val completeUrl =
                        "${mainEvent.urlOffer}&aff_sub1=${_state.value.affsub1Unswer}&aff_sub2=${_state.value.affsub2Unswer}&aff_sub3=${_state.value.affsub3Unswer}&aff_sub5=${_state.value.affsub5Unswer}"
                    Log.d("PushDemoLog", "url $completeUrl")
                    if (service.checkedInternetConnection()) {
                        when (val lastState = state.value.statusApplication) {
                            is StatusApplication.Connect -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = OFFER_WALL
                                )
                                when (lastState.baseState) {

                                    BaseState.Loans -> {
                                        sendFromListOffers(
                                            url = completeUrl,
                                            parameter = LOANS
                                        )
                                    }
                                }
                            }

                            /*is StatusApplication.Offer -> {
                                sendGoToOffer(
                                    url = completeUrl,
                                    parameter = MORE_DETAILS
                                )
                            }*/
                            is StatusApplication.Web -> {}
                            StatusApplication.Reconnect -> {}
                            StatusApplication.Welcome -> {}
                            //StatusApplication.FinanceGuide -> {}
                            StatusApplication.FinanceMarket -> {}
                            StatusApplication.FindLoan -> {}
                            StatusApplication.Terms -> {}
                            //StatusApplication.Privacy -> {}
                        }
                        _state.value.copy(
                            statusApplication = StatusApplication.Web(
                                url = completeUrl,
                                offerName = mainEvent.nameOffer
                            ),
                        )
                            .updateStateUI()
                    } else {
                        _state.value.copy(
                            lastState = StatusApplication.Web(
                                offerName = mainEvent.nameOffer,
                                url = completeUrl
                            ),
                            statusApplication = StatusApplication.Reconnect,
                        )
                            .updateStateUI()
                    }
                }
            }

            is Reconnect -> {
                if (service.checkedInternetConnection()) {
                    if (state.value.lastState !is StatusApplication.Welcome) {
                        _state.value.copy(
                            statusApplication = state.value.lastState
                        )
                            .updateStateUI()
                    } else {
                        viewModelScope.launch {
                            loadData()
                        }
                    }
                } else {
                    _state.value.copy(
                        statusApplication = StatusApplication.Reconnect,
                    )
                        .updateStateUI()
                }
            }

            is UpdateLastStatusApplication -> {
                _state.value.copy(
                    lastState = mainEvent.statusApplication,
                )
                    .updateStateUI()
            }

            MainEvent.SetNotFirstRun -> {
                viewModelScope.launch {
                    sharedKeeper.setFirstRun(false)
                }
            }
        }
    }


    private fun getSub1() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub1(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = _yandexMetrikaDeviceId.value,
                appsflyer = _appsFlayerInstanceId.value,
                firebaseToken = "NA",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    Log.d("FGHJJ", "result sub1 ${result.data}")
                    _state.value.copy(
                        affsub1Unswer = result.data?.affsub1 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub3() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub3(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appMetricaId = APP_METRICA,
                appsflyer = _appsFlayerInstanceId.value,
                token = _state.value.hmsToken ?: "",
                myTrackerId = _state.value.instanceIdMyTracker ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    Log.d("PushDemoLog", "result from sub3 ${result.data?.affsub3}")
                    _state.value.copy(
                        affsub3Unswer = result.data?.affsub3 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub5() {
        viewModelScope.launch {
            delay(2000)
            when (val result = repositoryAnalytic.getSub5(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                gaid = _state.value.gaid ?: ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    _state.value.copy(
                        affsub5Unswer = result.data?.affsub5 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getFirstSub2() {
        viewModelScope.launch {
            delay(1000)
            when (val result = repositoryAnalytic.getSub2(
                applicationToken = APY_KEY,
                userId = _state.value.gaid ?: "",
                appsflyer = "",
                myTracker = ""
            )) {
                is Resource.Error -> {
                    _state.value.copy(
                        message = result.message ?: "unknown error"
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    val sub2 = result.data?.affsub2
                    Log.d("ASDFGH", "affsub2UnswerEmpty $sub2")
                    _state.value.copy(
                        affsub2UnswerEmpty = sub2 ?: ""
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun getSub2(currentMyTracker: String, currentAppsFlyer: String) {
        viewModelScope.launch {
            if (currentMyTracker.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = "",
                    myTracker = currentMyTracker
                )) {
                    is Resource.Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Resource.Success -> {
                        Log.d("ASDFGH", "currentMyTracker $currentMyTracker")
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerMT $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerMT = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
            if (currentAppsFlyer.isNotBlank()) {
                when (val result = repositoryAnalytic.getSub2(
                    applicationToken = APY_KEY,
                    userId = _state.value.gaid ?: "",
                    appsflyer = currentAppsFlyer,
                    myTracker = ""
                )) {
                    is Resource.Error -> {
                        _state.value.copy(
                            message = result.message ?: "unknown error"
                        )
                            .updateStateUI()
                    }

                    is Resource.Success -> {
                        val affsub2Unswer = result.data?.affsub2 ?: ""
                        Log.d("ASDFGH", "affsub2UnswerAF $affsub2Unswer")
                        _state.value.copy(
                            affsub2UnswerAF = affsub2Unswer
                        )
                            .updateStateUI()
                    }
                }
            }
        }
    }

    private fun loadDbData() {

        viewModelScope.launch {
            _state.value.copy(
                isLoading = true,
            )
                .updateStateUI()
            delay(3000)
            val currentGaid = _state.value.gaid ?: ""

            Log.d("SDFGH", "oaid ${_state.value.gaid}")
            Log.d("SDFGH", "instanceMyTracker ${_state.value.instanceIdMyTracker}")
            Log.d("SDFGH", "-------------------------")
            val db = repositoryServer.getDataDb()
            YandexMetrica.reportEvent(REQUEST_DB, currentGaid)
            MyTracker.trackEvent(REQUEST_DB, mapOf(REQUEST_DB to currentGaid))
            service.sendAppsFlyerEvent(
                key = REQUEST_DB,
                content = mapOf(REQUEST_DB to currentGaid)
            )
            when (db) {
                is Resource.Error -> {
                    YandexMetrica.reportEvent(BACKEND_UNAVAILABLE, currentGaid)
                    MyTracker.trackEvent(
                        BACKEND_UNAVAILABLE,
                        mapOf(BACKEND_UNAVAILABLE to currentGaid)
                    )
                    service.sendAppsFlyerEvent(
                        key = BACKEND_UNAVAILABLE,
                        content = mapOf(BACKEND_UNAVAILABLE to currentGaid)
                    )
                    _state.value.copy(
                        isLoading = false
                    )
                        .updateStateUI()
                }

                is Resource.Success -> {
                    if (_link.value.isBlank() || _link.value == " ") {
                        viewModelScope.launch {
                            if (sharedKeeper.getFirstRun()) {
                                _state.value.copy(
                                    dbData = db.data,
                                    statusApplication = StatusApplication.FindLoan
                                )
                                    .updateStateUI()
                            } else {
                                _state.value.copy(
                                    dbData = db.data,
                                    statusApplication = StatusApplication.Connect(BaseState.Loans)
                                )
                                    .updateStateUI()
                            }
                        }
                        val sharedSub2 = sharedKeeper.getSub2()
                        if (!sharedSub2.isNullOrBlank()) {
                            _state.value.copy(
                                affsub2Unswer = sharedSub2
                            )
                                .updateStateUI()
                        } else {
                            delay(2000)
                            getSub2(
                                currentAppsFlyer = _appsFlayer.value,
                                currentMyTracker = _myTracker.value
                            )
                            delay(2000)
                            val tempSub2 = if (_state.value.affsub2UnswerMT.isNotBlank()) {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerMT)
                                _state.value.affsub2UnswerMT
                            } else if (state.value.affsub2UnswerAF.isNotBlank()) {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerAF)
                                _state.value.affsub2UnswerAF
                            } else {
                                sharedKeeper.setSub2(_state.value.affsub2UnswerEmpty)
                                _state.value.affsub2UnswerEmpty
                            }
                            _state.value.copy(
                                affsub2Unswer = tempSub2
                            )
                                .updateStateUI()
                        }
                    } else {
                        delay(1000)
                        val statusApplication = _link.value.setStatusByPush(
                            loans = db.data?.loans ?: emptyList(),
                        )
                        _state.value.copy(
                            statusApplication = statusApplication,
                            dbData = db.data,
                        )
                            .updateStateUI()
                        delay(1000)
                    }
                    _state.value.copy(
                        isLoading = false
                    )
                        .updateStateUI()
                }
            }
        }
    }

    private fun sendGoToOffer(url: String, parameter: String) {
        val sendingData = mapOf(
            ITEM_ID to parameter,
            URL to url
        )
        YandexMetrica.reportEvent(EXTERNAL_LINK, sendingData)
        MyTracker.trackEvent(EXTERNAL_LINK)
        service.sendAppsFlyerEvent(
            key = EXTERNAL_LINK,
            content = sendingData
        )
    }

    private fun sendFromListOffers(url: String, parameter: String) {
        val sendingData = mapOf(
            URL to url
        )
        YandexMetrica.reportEvent(parameter, sendingData)
        MyTracker.trackEvent(parameter, sendingData)
        service.sendAppsFlyerEvent(
            key = parameter,
            content = sendingData
        )
    }

    private fun checkInternet(): Boolean {
        return service.checkedInternetConnection()
    }
}