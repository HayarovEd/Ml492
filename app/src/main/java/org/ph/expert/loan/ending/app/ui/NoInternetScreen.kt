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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.ui.theme.black
import org.ph.expert.loan.ending.app.ui.theme.violet
import org.ph.expert.loan.ending.app.ui.theme.white

@Preview
@Composable
fun NoInternetScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    BackHandler {}
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = white,
        bottomBar = {
            Button(
                modifier = modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 50.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = violet
                ),
                contentPadding = PaddingValues(vertical = 10.dp),
                onClick = onClick) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.reconnect),
                    style = TextStyle(
                        fontSize = 18.sp,
                        //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                        fontWeight = FontWeight(700),
                        color = white,
                    )
                )
            }
        }
    ) { paddins ->
        Column(
            modifier = modifier
                .padding(paddins)
                .fillMaxSize()
                .padding(25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = modifier
                    .fillMaxWidth(0.5f),
                painter = painterResource(id = R.drawable.no_connect),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = modifier.height(20.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.not_connect),
                style = TextStyle(
                    fontSize = 30.sp,
                    //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                    fontWeight = FontWeight(700),
                    color = black,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = modifier.height(10.dp))
            Text(
                modifier = modifier.fillMaxWidth(),
                text = stringResource(R.string.try_connect),
                style = TextStyle(
                    fontSize = 13.sp,
                    // fontFamily = FontFamily(Font(R.font.MTS Sans)),
                    fontWeight = FontWeight(700),
                    color = black,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}