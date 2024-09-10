package org.ph.expert.loan.ending.app.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.ui.theme.black
import org.ph.expert.loan.ending.app.ui.theme.violet
import org.ph.expert.loan.ending.app.ui.theme.white

@Preview(name = "NEXUS_7", device = Devices.NEXUS_7)
@Preview(name = "NEXUS_7_2013", device = Devices.NEXUS_7_2013)
@Preview(name = "NEXUS_5", device = Devices.NEXUS_5)
@Preview(name = "NEXUS_6", device = Devices.NEXUS_6)
@Preview(name = "NEXUS_5X", device = Devices.NEXUS_5X)
@Preview(name = "NEXUS_6P", device = Devices.NEXUS_6P)
@Preview(name = "PIXEL", device = Devices.PIXEL)
@Preview(name = "PIXEL_XL", device = Devices.PIXEL_XL)
@Preview(name = "PIXEL_2", device = Devices.PIXEL_2)
@Preview(name = "PIXEL_2_XL", device = Devices.PIXEL_2_XL)
@Preview(name = "PIXEL_3", device = Devices.PIXEL_3)
@Preview(name = "PIXEL_3_XL", device = Devices.PIXEL_3_XL)
@Preview(name = "PIXEL_3A", device = Devices.PIXEL_3A)
@Preview(name = "PIXEL_3A_XL", device = Devices.PIXEL_3A_XL)
@Preview(name = "PIXEL_4", device = Devices.PIXEL_4)
@Preview(name = "PIXEL_4_XL", device = Devices.PIXEL_4_XL)
@Composable
fun FinanceMarketScreen(
    modifier: Modifier = Modifier,
    onClickPrev: () -> Unit = {},
    onClickNext: () -> Unit = {},
) {
    BackHandler {
       onClickPrev()
    }
    Scaffold (
        modifier = modifier.fillMaxSize(),
        containerColor = white,
        bottomBar = {
            Button(
                modifier = modifier
                    .padding(start = 50.dp, end = 50.dp, bottom = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = violet
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                onClick = onClickNext) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.next),
                    style = TextStyle(
                        fontSize = 18.sp,
                        //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                        fontWeight = FontWeight(700),
                        color = white,
                    )
                )
            }
        }
    ) { paddings->
        Column (
            modifier = modifier
                .padding(paddings)
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                modifier = modifier
                    .fillMaxWidth(0.9f),
                painter = painterResource(id = R.drawable.fin_market),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = modifier.height(30.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.micro_loans),
                style = TextStyle(
                    fontSize = 36.sp,
                    //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                    fontWeight = FontWeight(700),
                    color = black,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = modifier.height(25.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.choose_loans),
                style = TextStyle(
                    fontSize = 18.sp,
                    //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                    fontWeight = FontWeight(400),
                    color = black,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}