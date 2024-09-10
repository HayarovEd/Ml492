package org.ph.expert.loan.ending.app.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.ph.expert.loan.ending.app.R
import org.ph.expert.loan.ending.app.ui.theme.black
import org.ph.expert.loan.ending.app.ui.theme.darkBlue
import org.ph.expert.loan.ending.app.ui.theme.white


@Composable
fun ItemLoan(
    modifier: Modifier = Modifier,
    screen: String,
    name: String,
    amount: String,
    onClickWeb: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = white
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    modifier = modifier
                        .weight(1f)
                        .clickable(onClick = onClickWeb),
                    model = screen,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = modifier.width(10.dp))
                Column(
                    modifier = modifier.weight(2f)
                ) {
                    Text(
                        modifier = modifier,
                        text = name,
                        style = TextStyle(
                            fontSize = 19.sp,
                            //fontFamily = FontFamily(Font(R.font.Gilroy)),
                            fontWeight = FontWeight(400),
                            color = black,
                        )
                    )
                    Spacer(modifier = modifier.height(5.dp))
                    Text(
                        modifier = modifier,
                        text = amount,
                        style = TextStyle(
                            fontSize = 22.sp,
                            //fontFamily = FontFamily(Font(R.font.Gilroy)),
                            fontWeight = FontWeight(400),
                            color = black,
                        )
                    )
                }
                Spacer(modifier = modifier.width(10.dp))
                IconButton(
                    modifier = modifier
                        .weight(1f),
                    onClick = onClickWeb
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_arrow_forward_37),
                        contentDescription = "",
                        tint = darkBlue
                    )
                }
            }
        }
    }
}