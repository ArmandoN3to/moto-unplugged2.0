package com.example.motounplugged.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable
fun AboutApp(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Text(
            text = "Sobre o aplicativo",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Sobre o Moto Unplugged",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Versão 1.0",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun AboutAppPreview() {
//    MotoUnpluggedTheme {
//        val navController = rememberNavController()
//        AboutApp(
//            onClick: () -> Unit
//        )
//    }
//}