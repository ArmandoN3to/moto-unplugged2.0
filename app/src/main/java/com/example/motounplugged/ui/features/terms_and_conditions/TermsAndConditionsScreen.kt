package com.example.motounplugged.ui.features.terms_and_conditions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.ui.components.TitleTextComponents
import com.example.motounplugged.R
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.components.TextComponent
import com.example.motounplugged.ui.theme.Gray20

@Composable
fun TermsAndConditionsScreen(
    onNavigateToTermsAndConditions: () -> Unit,
    onBack: () -> Unit,
    onClick: () -> Unit
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
            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { onBack() },
                        modifier = Modifier
                        .fillMaxWidth()
                    .heightIn(48.dp),
                contentPadding = PaddingValues(),
                colors = ButtonDefaults.buttonColors(Color.Transparent))
            {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(48.dp)
                        .background(
                            brush = Brush.horizontalGradient(listOf(Gray20, Color.Gray)),
                            shape = RoundedCornerShape(50.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Voltar",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
        }

    }
}
}

@Preview
@Composable
fun TermsAndConditionsScreenPreview(){
    TermsAndConditionsScreen(
        onNavigateToTermsAndConditions={},
        onBack = {},
        onClick = {}
    )
}