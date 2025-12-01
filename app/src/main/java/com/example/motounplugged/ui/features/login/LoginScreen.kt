package com.example.motounplugged.ui.features.login

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.motounplugged.ui.theme.BgColor
import com.example.motounplugged.ui.theme.Gray20
import com.example.motounplugged.ui.theme.MotoUnpluggedTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

//SHA criptografia
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,

){
    //val viewModel: LoginScreenViewModel = koinViewModel()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var forgotPasswordDialogBox by remember { mutableStateOf(false) }

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

            Spacer(modifier = Modifier.height(10.dp))

            if (forgotPasswordDialogBox) {
                var resetEmail by remember { mutableStateOf("") }
                val context = LocalContext.current

                AlertDialog(
                    title = { Text("   Esqueceu sua senha?", color = Color.DarkGray) },
                    text = {
                        val smal : CornerBasedShape = RoundedCornerShape(13.dp)
                        OutlinedTextField(
                            value = resetEmail,
                            onValueChange = { resetEmail = it },
                            label = {Text(text = "Registered email", color = Color.Gray)},
                            shape = smal,
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Gray,
                                unfocusedIndicatorColor = BgColor, //cor da borda
                                focusedLabelColor = Color.Gray,// cor do texto titulo
                                cursorColor = Color.Gray, // cor do cursor
                                unfocusedContainerColor = BgColor,//cor do componente sem clicar
                                focusedContainerColor = Color.Transparent,//cor de dentro no componente(fundo)
                                disabledContainerColor = Color.Gray //cor da borda ao clicar para escrever
                            ),
                            keyboardOptions = KeyboardOptions.Default,
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (resetEmail.isNotBlank()) {
                                    Firebase.auth.sendPasswordResetEmail(resetEmail)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Check your email to reset password",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                forgotPasswordDialogBox = false
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Registered email not found",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please enter your registered email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Text("Submit", color = Color.DarkGray)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { forgotPasswordDialogBox = false }) {
                            Text("Cancel", color = Color.DarkGray)
                        }
                    },
                    onDismissRequest = { forgotPasswordDialogBox = false }
                )
            }

            TextButton(
                onClick = { forgotPasswordDialogBox = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            ) {

                Text("Forgot Password?", color = Color.Gray)
            }



            Spacer(modifier = Modifier.height(100.dp))
            val context = LocalContext.current
            ButtonComponent(value = stringResource(id = R.string.login),
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(
                            context,
                            "Por favor, preencha todos os campos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Firebase.auth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener{ task ->
                                if (task.isSuccessful){
                                    Toast.makeText(context,"Login successful!",
                                        Toast.LENGTH_SHORT).show()
                                    onLoginSuccess()

                                } else{
                                    Toast.makeText(context,
                                        task.exception?.message?: "Login failed",
                                        Toast.LENGTH_SHORT).show()
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