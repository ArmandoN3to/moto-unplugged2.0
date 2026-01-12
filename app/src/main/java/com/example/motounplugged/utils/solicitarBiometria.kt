package com.example.motounplugged.utils


import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.motounplugged.database.entities.ProfilesEntity

// Função auxiliar para encontrar a Activity a partir do Contexto do Compose
fun Context.findActivity(): FragmentActivity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is FragmentActivity) return context
        context = context.baseContext
    }
    return null
}

fun solicitarBiometria(
    context: Context,
    onSuccess: () -> Unit

) {
    val activity = context.findActivity()


    if (activity == null) {
        Toast.makeText(context, "Erro: Activity não compatível", Toast.LENGTH_SHORT).show()
        return
    }

    val executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess()
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode != BiometricPrompt.ERROR_USER_CANCELED) {
                    Toast.makeText(context, "Erro: $errString", Toast.LENGTH_SHORT).show()
                }
            }
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Confirmação Necessária")
        .setSubtitle("Valide sua identidade para desativar o bloqueio")
        .setNegativeButtonText("Cancelar")
        .build()

    biometricPrompt.authenticate(promptInfo)
}