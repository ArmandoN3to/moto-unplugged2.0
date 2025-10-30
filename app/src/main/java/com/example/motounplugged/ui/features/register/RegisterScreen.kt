package com.example.motounplugged.ui.features.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme

@Composable

fun RegisterScreen(
    modifier : Modifier = Modifier
){
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .padding(top = 20.dp)
            .padding(start = 10.dp, end = 10.dp)
            .background(color = Color.Magenta)
    ){
        Column(
            modifier =  Modifier
                .align(Alignment.CenterHorizontally)
                .background(color = Color.Gray)
                .padding(start = 10.dp, end = 10.dp)

        ){
            var text_name by remember { mutableStateOf("") }
            var text_email by remember { mutableStateOf("") }
            var text_senha by remember { mutableStateOf("") }
            var text_senha_confirm by remember { mutableStateOf("") }

            Text(
                text = "Create Account",
                fontStyle = FontStyle.Normal,
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 20.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            TextField(
                value = text_name,
                onValueChange = { text_name = it },
                label = {
                    Text("Name", fontSize = 15.sp) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "name icon",
                        modifier = Modifier
                            .size(width = 30.dp, height = 18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp)


            )
            TextField(
                value = text_email,
                onValueChange = { text_email= it },
                label = { Text("Email") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "name icon",
                        modifier = Modifier
                            .size(width = 30.dp, height = 18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp)
            )
            TextField(
                value = text_senha,
                onValueChange = { text_senha = it},
                label = { Text("Senha") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "name icon",
                        modifier = Modifier
                            .size(width = 30.dp, height = 18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 15.dp)
            )
            TextField(
                value = text_senha_confirm,
                onValueChange = { text_senha_confirm = it},
                label = { Text("Confirmar Senha") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "name icon",
                        modifier = Modifier
                            .size(width = 30.dp, height = 18.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 15.dp)
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