package com.github.biometricsample

import android.os.Bundle
import android.widget.Toast
import androidx.biometric.BiometricManager
import android.view.View
import kotlinx.android.synthetic.main.activity_biometric_auth.*


class BiometricAuthActivity : BaseActivity(),BaseActivity.AuthSuccessListner {

    var biometricManager: BiometricManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biometric_auth)
        megan_image.visibility = View.GONE

        //call to show prompt
        show_auth_prompt.setOnClickListener {
            initBioMetric()
        }
    }

    private fun initBioMetric() {
        biometricManager = BiometricManager.from(this)
        when (biometricManager?.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                showBiometricPrompt(
                    this,
                    resources.getString(R.string.biometric_title_text),
                    resources.getString(R.string.biometric_subtitle_text),
                    resources.getString(R.string.biometric_description_text),
                    resources.getString(R.string.cancel_text),this
                )
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(
                    this,
                    resources.getString(R.string.compatibilty_error_text),
                    Toast.LENGTH_SHORT
                ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                    this,
                    resources.getString(R.string.compatibilty_error_text),
                    Toast.LENGTH_SHORT
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(
                    this,
                    resources.getString(R.string.biometric_no_enrolled_text),
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    override fun verificationSuccessful() {
        megan_image.visibility = View.VISIBLE
    }
}
