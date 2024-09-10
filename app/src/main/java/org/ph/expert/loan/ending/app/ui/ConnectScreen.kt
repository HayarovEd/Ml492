package org.ph.expert.loan.ending.app.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.domain.model.Loan
import org.ph.expert.loan.ending.app.ui.state.MainEvent
import org.ph.expert.loan.ending.app.ui.theme.darkBlue
import org.ph.expert.loan.ending.app.ui.theme.white

@Composable
fun ConnectScreen(
    modifier: Modifier = Modifier,
    event: (MainEvent) -> Unit,
    loanLazyState: LazyListState,
    loans: List<Loan>,
) {
    Scaffold(
        contentColor = white,
        topBar = {
            Text(
                modifier = modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(),
                text = stringResource(R.string.loans),
                style = TextStyle(
                    fontSize = 22.sp,
                    //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                    fontWeight = FontWeight(700),
                    color = darkBlue,
                    textAlign = TextAlign.Center
                )
            )
        },
    ) { paddings ->
        LazyColumn(
            modifier = modifier
                .padding(paddings)
                .fillMaxSize(),
            state = loanLazyState,
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(vertical = 10.dp, horizontal = 20.dp)
        ) {
            items(loans) { loan ->
                ItemLoan(
                    name = loan.name,
                    screen = loan.screen,
                    amount = loan.amounts,
                    onClickWeb = {
                        event(
                            MainEvent.OnGoToWeb(
                                urlOffer = loan.order,
                                nameOffer = loan.name
                            )
                        )
                    }
                )
            }
        }
    }
}