package com.github.biometricsample

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt

/**
 * Created by jadhavpankaj16 on 2019-09-03
 */
abstract class BaseActivity : AppCompatActivity() {

    interface AuthSuccessListner {
        fun verificationSuccessful()
    }

    @TargetApi(Build.VERSION_CODES.P)
    fun showBiometricPrompt(
        context: Context,
        title: String,
        subtitle: String,
        description: String,
        negativeBtnText: String, authListener: AuthSuccessListner) {

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            //.setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText(negativeBtnText)
            .build()

        val biometricPrompt = BiometricPrompt(this, context.mainExecutor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    authListener.verificationSuccessful()
                    //Crypto-object will get from here
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(applicationContext, "Authentication failed", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        //invoke fingerprint dialog
        biometricPrompt.authenticate(promptInfo)
    }
}