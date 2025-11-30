package com.example.motounplugged.ui.features.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.motounplugged.R
import com.example.motounplugged.R.string.hello
import com.example.motounplugged.ui.components.ButtonComponent
import com.example.motounplugged.ui.components.CheckboxComponent
import com.example.motounplugged.ui.components.ClickableLoginTextComponent
import com.example.motounplugged.ui.components.DividerTextComponent
import com.example.motounplugged.ui.components.MyTextField
import com.example.motounplugged.ui.components.NormalTextComponents
import com.example.motounplugged.ui.components.PasswordTextField
import com.example.motounplugged.ui.components.TitleTextComponents
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun RegisterScreen(
    onRegisterComplete: () -> Unit,
    onBackToLogin: () -> Unit,
    onNavigateToTermsAndConditions: () -> Unit,
    viewModel: RegisterScreenViewModel = koinViewModel()
){
    val context = LocalContext.current

    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(28.dp)
            .padding(top = 50.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ){
            Spacer(modifier = Modifier.height(50.dp))

            NormalTextComponents(value = stringResource(id = hello))
            TitleTextComponents(value = stringResource(id = R.string.create_account))

            Spacer(modifier = Modifier.height(20.dp))

            MyTextField(
                labelValue = stringResource(id = R.string.first_name),
                painterResource(id = R.drawable.person_register),
                value = viewModel.firstName,
                onValueChange = {viewModel.firstName = it}
            )

            MyTextField(
                labelValue = stringResource(id = R.string.last_name),
                painterResource = painterResource(id = R.drawable.person_register),
                value = viewModel.lastName,
                onValueChange = {viewModel.lastName = it}
            )

            MyTextField(
                labelValue = stringResource(id = R.string.email),
                painterResource = painterResource(id = R.drawable.email),
                value = viewModel.email,
                onValueChange = {viewModel.email = it}
            )

            PasswordTextField(
                labelValue = stringResource(id = R.string.password),
                painterResource = painterResource(id = R.drawable.password),
                value = viewModel.password,
                onValueChange = {viewModel.password = it}
            )

            CheckboxComponent(
                value = stringResource(id = R.string.terms_and_conditions),
                checked = viewModel.checkedState,
                onCheckedChange = {viewModel.checkedState = it},
                onTextSelected = {
                    onNavigateToTermsAndConditions()
                })

            Spacer(modifier = Modifier.height(100.dp))

            ButtonComponent(value = stringResource(id = R.string.register),
                onClick = {
                    if (viewModel.firstName.isBlank() || viewModel.lastName.isBlank() || viewModel.email.isBlank() || viewModel.password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor, preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (!viewModel.checkedState){
                        Toast.makeText(
                                context,
                                "Vocẽ deve aceitar os Termos de Serviços e Políticas de Privacidade",
                                Toast.LENGTH_SHORT
                                ).show()
                    }else {
                        viewModel.registerUser(viewModel.firstName, viewModel.lastName, viewModel.email, viewModel.password)
                        Toast.makeText(
                            context,
                            "Usuário cadastrado com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                        //onregistercomplete esta na main e serve para verificar se a os campos não estão vazios
                        onRegisterComplete()
                    }
                })
            Spacer(modifier = Modifier.height(25.dp))
            DividerTextComponent()
            ClickableLoginTextComponent(tryingToLogin = true, onTextSelected = {
                onBackToLogin()
            })




        }

    }

}


@Preview(showBackground = true)
@Composable
fun registerscreen(){
    MotoUnpluggedTheme {
        RegisterScreen(onRegisterComplete = {}, onBackToLogin = {},onNavigateToTermsAndConditions={})
    }
}