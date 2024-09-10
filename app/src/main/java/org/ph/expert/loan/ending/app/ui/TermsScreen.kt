package org.ph.expert.loan.ending.app.ui

import android.widget.TextView
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.ui.theme.darkBlue
import org.ph.expert.loan.ending.app.ui.theme.violet
import org.ph.expert.loan.ending.app.ui.theme.white

@Preview
@Composable
fun TermsScreen(
    modifier: Modifier = Modifier,
    content: String = "",
    onBackClick: () -> Unit = {},
    onClickNext: () -> Unit = {},
) {
    BackHandler {
        onBackClick()
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = white,
        topBar = {
            Row(
                modifier = modifier
                    .padding(start = 20.dp, end = 20.dp, top = 35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                /*IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = "",
                        tint = black
                    )
                }
                Spacer(modifier = modifier.width(10.dp))*/
                Text(
                    text = stringResource(id = R.string.policy),
                    style = TextStyle(
                        fontSize = 22.sp,
                        //fontFamily = FontFamily(Font(R.font.MTS Sans)),
                        fontWeight = FontWeight(700),
                        color = darkBlue
                    )
                )
            }
        },
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
                onClick = onClickNext) {
                Text(
                    modifier = modifier,
                    text = stringResource(R.string.confirm),
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
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AndroidView(
                modifier = modifier
                    .fillMaxWidth(),
                factory = { context -> TextView(context) },
                update = {
                    it.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT)
                }
            )
        }
    }
}