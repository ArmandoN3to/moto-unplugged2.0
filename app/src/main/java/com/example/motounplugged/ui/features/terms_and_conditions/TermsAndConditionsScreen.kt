package com.example.motounplugged.ui.features.terms_and_conditions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motounplugged.ui.components.TitleTextComponents
import com.example.motounplugged.R
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.components.TextComponent

@Composable
fun TermsAndConditionsScreen(
    onNavigateToTermsAndConditions: () -> Unit
){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            TitleTextComponents(value = stringResource(id = R.string.terms_and_conditions_header))
            Spacer(modifier = Modifier.height(40.dp))
            NormalTextComponents(value = stringResource(id = R.string.Privacy_Policy))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.Privacy_Policy_Text))
            Spacer(modifier = Modifier.height(20.dp))
            NormalTextComponents(value = stringResource(id = R.string.Terms_of_use))
            Spacer(modifier = Modifier.height(10.dp))
            TextComponent(value = stringResource(id = R.string.Terms_of_use_text))

        }

    }
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    TermsAndConditionsScreen(onNavigateToTermsAndConditions={})
}