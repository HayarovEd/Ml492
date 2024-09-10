package org.ph.expert.loan.ending.app.ui


import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.ui.theme.violet

@Preview
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier
) {
    BackHandler {}
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = violet)
        .padding(27.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.welcome),
            contentDescription = "",
            contentScale = ContentScale.FillWidth)
    }
}