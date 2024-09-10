package org.ph.expert.loan.ending.app.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.ph.expert.loan.ending.app.domain.model.BaseState
import org.ph.expert.loan.ending.app.domain.model.StatusApplication
import org.ph.expert.loan.ending.app.ui.state.MainEvent
import org.ph.expert.loan.ending.app.ui.state.MainViewModel

@Composable
fun BaseScene(
    viewModel: MainViewModel,
) {

    val state = viewModel.state.collectAsState()

    val loanLazyState = rememberLazyListState()

    val event = viewModel::onEvent
    when (val currentState = state.value.statusApplication) {
        is StatusApplication.Connect -> {
            /*ConnectScreen(
                loanLazyState = loanLazyState,
                event = event,
                loans = state.value.dbData?.loans ?: emptyList()
            )*/
        }

        /*StatusApplication.FinanceGuide -> {
            FinanceGuideScreen(
                onClickNext = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.FinanceMarket))
                },
                onClickPrev = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.FindLoan))
                }
            )
        }*/

        StatusApplication.FinanceMarket -> {
            FinanceMarketScreen(
                onClickNext = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.Terms))
                    /*event(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Connect(
                                BaseState.Loans
                            )
                        )
                    )
                    event(
                        MainEvent.SetNotFirstRun
                    )*/
                },
                onClickPrev = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.FindLoan))
                    //event(MainEvent.OnChangeStatusApplication(StatusApplication.FinanceGuide))
                },
                /*onPrivacyClick = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.Terms))
                }*/
            )
        }

        StatusApplication.FindLoan -> {
            FindLoanScreen(
                onClickNext = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.FinanceMarket))
                    //event(MainEvent.OnChangeStatusApplication(StatusApplication.FinanceGuide))
                }
            )
        }


        StatusApplication.Reconnect -> {
            /* NoInternetScreen(
                 onClick = {
                     event(MainEvent.Reconnect)
                 }
             )*/
        }

        StatusApplication.Terms -> {
            TermsScreen(
                content = state.value.dbData?.appConfig?.privacyPolicyHtml ?: "",
                onBackClick = {
                    event(MainEvent.OnChangeStatusApplication(StatusApplication.FinanceMarket))
                },
                onClickNext = {
                    event(
                        MainEvent.OnChangeStatusApplication(
                            StatusApplication.Connect(
                                BaseState.Loans
                            )
                        )
                    )
                    event(
                        MainEvent.SetNotFirstRun
                    )
                }
            )
        }

        is StatusApplication.Web -> {
            /*WebViewScreen(
                url = currentState.url,
                offerName = currentState.offerName,
                onEvent = event
            )*/
        }

        StatusApplication.Welcome -> {
            WelcomeScreen()
        }

        /*is StatusApplication.Offer -> {
            OfferScreen(
                loan = currentState.loan,
                onBackClick = {
                    event(
                        MainEvent.OnChangeStatusApplication(
                            statusApplication = StatusApplication.Connect(
                                BaseState.Loans
                            )
                        )
                    )
                },
                onClickWeb = {
                    event(
                        MainEvent.OnGoToWeb(
                            urlOffer = currentState.loan.order,
                            nameOffer = currentState.loan.name
                        )
                    )
                }
            )
        }*/
    }
}