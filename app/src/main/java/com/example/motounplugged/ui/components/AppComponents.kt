@file:Suppress("UNUSED_EXPRESSION")

package com.example.motounplugged.ui.components

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.motounplugged.R
import com.example.motounplugged.ui.theme.BgColor
import com.example.motounplugged.ui.theme.Gray20
import com.example.motounplugged.ui.theme.Primary

//componente de titulo principal
@Composable
fun NormalTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}
//componente de titulo
@Composable
fun TitleTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorText),
        textAlign = TextAlign.Center
    )
}
//componente de texto de inserir dados
@Composable
fun MyTextField(labelValue: String, painterResource: Painter){

    val textValue = remember {
        mutableStateOf("")
    }
    val smal : CornerBasedShape = RoundedCornerShape(13.dp)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        value = textValue.value,
        shape = smal,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = Gray20,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = BgColor,
            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            errorContainerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = " ")
        }

        )


}

//componente de senha
@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter){

    val password = remember {
        mutableStateOf("")
    }
    val passwordVisible = remember {
        mutableStateOf(false)
    }
    val smal : CornerBasedShape = RoundedCornerShape(13.dp)
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        label = {Text(text = labelValue)},
        value = password.value,
        shape = smal,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Primary,
            unfocusedIndicatorColor = Gray20,
            focusedLabelColor = Primary,
            cursorColor = Primary,
            unfocusedContainerColor = BgColor,
            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            errorContainerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            password.value = it
        },
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = " ")
        },
        //tornar a senha visivel ou não
        trailingIcon = {
            val iconImage = if (passwordVisible.value){
                Icons.Filled.Visibility
            }else{
                Icons.Filled.VisibilityOff
            }
            var description = if(passwordVisible.value){
                stringResource(id = R.string.hide_password)
            }else{
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        //verificar se a senha esta visual ou não e aplicar a transformação
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()

    )


}

//componente de checklist do termos e serviços
@Composable
fun CheckboxComponent(value: String){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val checkedState = remember {
            mutableStateOf<Boolean>(false)
        }
        Checkbox(checked = checkedState.value,
            onCheckedChange = { it
                checkedState.value = !checkedState.value
            })
        NormalTextComponents(value)

    }
}

//componente da string os termos e serviços
@Composable
fun ClickableTextComponent(value: String){
    val initialText = "By continuing you accept our"
    val privacyPolicyText = "Privacy Policy"
    val andText = "and"
    val termsAndConditionsText = "Terms of Use"

    //construtor para criar a string de trms e serviços
    val annotatedString = buildAnnotatedString {
        append(initialText)
        //definir o estilo do meu privacy policy
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Primary)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }
    //offset deslocamento ao clicar do text
    ClickableText(text = annotatedString, onClick = { offset ->
        //offset no inicio de fim para verificar se há deslocamento
        annotatedString.getStringAnnotations(offset,offset)
            //verifica se houver um clique valido span, also verifica se esta retornando uma string anotada
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{$span}")

            }


    })
}