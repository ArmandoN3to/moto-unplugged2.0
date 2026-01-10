package com.example.motounplugged.utils

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity

fun authenticateUser(
    context: Context,
    onSuccess: () -> Unit,
    onError: () -> Unit = {}
) {
    // A biblioteca biometrica exige FragmentActivity
    val activity = context as? FragmentActivity ?: return

    val executor = ContextCompat.getMainExecutor(activity)

    val biometricPrompt = BiometricPrompt(activity, executor,
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onSuccess() // Chama o callback de sucesso
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(context, "Erro: $errString", Toast.LENGTH_SHORT).show()
                onError()
            }

            // Opcional: Tratar onAuthenticationFailed (leitura ruim, mas tenta de novo)
        })

    val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Confirmação necessária")
        .setSubtitle("Valide sua biometria para desativar esta função")
        .setNegativeButtonText("Cancelar")
        .build()

    biometricPrompt.authenticate(promptInfo)
}