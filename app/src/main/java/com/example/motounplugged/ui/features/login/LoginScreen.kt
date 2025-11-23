package com.example.motounplugged.ui.features.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motounplugged.R
import com.example.motounplugged.ui.components.ButtonComponent
import com.example.motounplugged.ui.components.ClickableLoginTextComponent
import com.example.motounplugged.ui.components.DividerTextComponent
import com.example.motounplugged.ui.components.MyTextField
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.components.PasswordTextField
import com.example.motounplugged.ui.components.TitleTextComponents
import com.example.motounplugged.ui.components.UnderLinedTextComponents
import com.example.motounplugged.ui.features.register.RegisterScreen
import com.example.motounplugged.ui.navigation.AppNavHost
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

//SHA criptografia
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,

){
    val viewModel: LoginScreenViewModel = koinViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(28.dp)
            .padding(top = 50.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            NormalTextComponents(value = stringResource(id = R.string.login))
            TitleTextComponents(value = stringResource(id = R.string.welcome))
            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                labelValue = stringResource(id = R.string.email),
                painterResource(id = R.drawable.email),
                value = email,
                onValueChange = {email = it})

            PasswordTextField(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.password),
                value = password,
                onValueChange = {password = it}
            )

            Spacer(modifier = Modifier.height(30.dp))
            UnderLinedTextComponents(value = stringResource(id = R.string.forgot_password))

            Spacer(modifier = Modifier.height(100.dp))
            ButtonComponent(value = stringResource(id = R.string.login),
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor, preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        //COLOCAR AS COFIGURAÇÕES DA VIEWMODEL DA LOGIN REGISTER QUE
                        //VAI RECEBER O BANCO DE DADOS DA TABELA DE REGISTRO
                        coroutineScope.launch {
                            val isValid = viewModel.login(email,password)
                            if (isValid){
                                onLoginSuccess()
                            } else{
                                Toast.makeText(context, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            DividerTextComponent()
            ClickableLoginTextComponent(tryingToLogin = false, onTextSelected = {
                onNavigateToRegister()
            })

        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun LoginScreenPreview(){
    MotoUnpluggedTheme {
        LoginScreen(onLoginSuccess = {},onNavigateToRegister = {})
    }
}