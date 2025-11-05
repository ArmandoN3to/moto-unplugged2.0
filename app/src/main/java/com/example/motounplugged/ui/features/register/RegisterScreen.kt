package com.example.motounplugged.ui.features.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motounplugged.R
import com.example.motounplugged.R.string.hello
import com.example.motounplugged.ui.components.MyTextField
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.components.TitleTextComponents
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun RegisterScreen(){

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            NormalTextComponents(value = stringResource(id = hello))
            TitleTextComponents(value = stringResource(id = R.string.create_account))

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                labelValue = stringResource(id = R.string.first_name),
                painterResource(id = R.drawable.person_register)
                )

            MyTextField(
                labelValue = stringResource(id = R.string.last_name),
                painterResource = painterResource(id = R.drawable.person_register)
            )

            MyTextField(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email)
            )

            MyTextField(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.password),

            )


        }

    }

}


@Preview(showBackground = true)
@Composable
private fun registerscreen(){
    MotoUnpluggedTheme {
        RegisterScreen()
    }
}