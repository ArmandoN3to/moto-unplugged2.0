package com.example.motounplugged.ui.features.streak

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun StreakScreen(
    modifier: Modifier = Modifier,

) {
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){

        Row {
            Spacer(modifier = Modifier .weight(1f))
            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share button",
                    modifier = Modifier
                        .size(width = 60.dp, height = 100.dp)
                        .padding(start = 16.dp)
                        .padding(top = 7.dp)
                )
            }
        }
        Column (
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Ofensivas",
                fontSize = 25.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Monitore suas ofensivas e progressos",
                fontSize = 20.sp,
                fontStyle = FontStyle.Normal,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = 5.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding( top = 30.dp)
        ){
            Row {

                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Star icon",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(width = 150.dp, height = 100.dp)
                        .padding(start = 16.dp)
                        .padding(top = 7.dp)
                )

                Spacer(modifier = Modifier .weight(1f))

                Column (
                    modifier = Modifier
                    .padding(top = 45.dp)
                        .padding(10.dp)
                        .border(
                            width = 2.dp,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(5.dp),
                        )
                ){
                    Text(
                        text = "1",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier =  Modifier
                            .padding(start = 90.dp)
                    )
                    Text(
                        text = "Dias  de Ofensivas",
                        fontStyle = FontStyle.Italic,
                        fontSize = 20.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                    )
                }

            }


        }



    }
}

@Preview(showBackground = true)
@Composable
private fun streakscreenview(){
    MotoUnpluggedTheme {
        StreakScreen()
    }
}