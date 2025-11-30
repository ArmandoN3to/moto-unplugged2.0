@file:Suppress("UNUSED_EXPRESSION")

package com.example.motounplugged.ui.components

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.NumberPicker.OnValueChangeListener
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import androidx.compose.ui.text.style.TextDecoration
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
fun MyTextField(labelValue: String, painterResource: Painter,value: String, onValueChange: (String) -> Unit){

    val smal : CornerBasedShape = RoundedCornerShape(13.dp)

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
        value = value,
        shape = smal,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Gray20,
            focusedLabelColor = Color.Gray,
            cursorColor = Color.Gray,
            unfocusedContainerColor = BgColor,
            focusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource,
                contentDescription = " ")
        }

        )


}

//componente de senha
@Composable
fun PasswordTextField(labelValue: String, painterResource: Painter, value: String, onValueChange: (String) -> Unit ){

    val passwordVisible = remember {
        mutableStateOf(false)
    }
    val smal : CornerBasedShape = RoundedCornerShape(13.dp)

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
        label = {Text(text = labelValue)},
        value = value,
        shape = smal,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Gray,
            unfocusedIndicatorColor = Gray20,
            focusedLabelColor = Color.Gray,
            cursorColor = Color.Gray,
            unfocusedContainerColor = BgColor,
            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            errorContainerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = onValueChange,
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
            val description = if(passwordVisible.value){
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
fun CheckboxComponent(value: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit, onTextSelected : (String) -> Unit){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val checkedState = remember {
            mutableStateOf<Boolean>(false)
        }
        Checkbox(
            checked = checked,
            onCheckedChange = { onCheckedChange(it)}
        )
        ClickableTextComponent(value = value, onTextSelected)

    }
}

//componente da string os termos e serviços
@Composable
fun ClickableTextComponent(value: String, onTextSelected : (String) -> Unit){
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy "
    val andText = " and "
    val termsAndConditionsText = "Terms of Use"

    //construtor para criar a string de trms e serviços
    val annotatedString = buildAnnotatedString {
        append(initialText)
        //definir o estilo do meu privacy policy
        withStyle(style = SpanStyle(color = Color.Gray)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = Color.Gray)){
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
                Log.d("ClickableTextComponent", "{${span.item}")

                //verifica se o item esta clicavel e na rota
                if((span.item == termsAndConditionsText) || (span.item == privacyPolicyText)){
                    onTextSelected(span.item)

            }
    }})
}

//componente de texto sublinhado utilizado na tela de login
@Composable
fun UnderLinedTextComponents(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorGray),
        textAlign = TextAlign.Center,
        //adiciiona um sublinhado ao texto
        textDecoration = TextDecoration.Underline
    )
}

//botão de registro e login
@Composable
fun ButtonComponent(value: String,  onClick:() -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
    ) {
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
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

}

//divisor de componentes (-----or-----)
@Composable
fun DividerTextComponent(){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )

        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(id = R.string.or),
            fontSize = 18.sp,
            color = Color.DarkGray
        )

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = Color.Gray,
            thickness = 1.dp
        )
    }
}

//componente da string login e register para navegação
//se for true pertence a tela de register se for false
//pertence a tela de login
@Composable
fun ClickableLoginTextComponent(tryingToLogin:Boolean = true,onTextSelected :  (String) -> Unit){
    val initialText = if (tryingToLogin) "                      Already have an account? " else "                      Don't have an account yet?"
    val LoginText = if (tryingToLogin) " Login" else " Register"

    //construtor para criar a string de trms e serviços
    val annotatedString = buildAnnotatedString {
        append(initialText)
        //definir o estilo do meu privacy policy
        withStyle(style = SpanStyle(color = Color.Gray)){
            pushStringAnnotation(tag = LoginText, annotation = LoginText)
            append(LoginText)
        }

    }
    //offset deslocamento ao clicar do text
    ClickableText(text = annotatedString, onClick = { offset ->
        //offset no inicio de fim para verificar se há deslocamento
        annotatedString.getStringAnnotations(offset,offset)
            //verifica se houver um clique valido span, also verifica se esta retornando uma string anotada
            .firstOrNull()?.also { span ->
                Log.d("ClickableTextComponent", "{${span.item}")

                //verifica se o item esta clicavel e na rota
                if(span.item == LoginText){
                    onTextSelected(span.item)

                }
            }})
}

//componente de texto
@Composable
fun TextComponent(value:String){
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = colorResource(id = R.color.colorGray),
        textAlign = TextAlign.Justify,
        //adiciiona um sublinhado ao texto
        //textDecoration = TextDecoration.Underline
    )
}