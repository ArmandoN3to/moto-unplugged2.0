package com.example.motounplugged.ui.features.register

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.motounplugged.R
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun RegisterScreen(){

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
    ) {
        NormalTextComponents(value = stringResource(id = R.string.hello))
    }

}


@Preview(showBackground = true)
@Composable
private fun registerscreen(){
    MotoUnpluggedTheme {
        RegisterScreen()
    }
}