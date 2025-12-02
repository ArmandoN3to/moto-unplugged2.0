package com.example.motounplugged.ui.features.settings.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.ui.components.NavigationSettingsCard
import com.example.motounplugged.ui.components.GeneralSettingsCard
import com.example.motounplugged.ui.components.AboutApp
import com.example.motounplugged.ui.components.AppHeaderInfo


@Composable
fun AboutSettingsScreen(
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        AppHeaderInfo(
            icon = painterResource(id = (com.example.motounplugged.R.drawable.logoblack)),
            title = "Sobre o Moto Unplugged",
            version = "1.0.0"
        )

    }
}
