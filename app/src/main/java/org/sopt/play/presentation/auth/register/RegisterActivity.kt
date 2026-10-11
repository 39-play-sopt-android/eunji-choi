package org.sopt.play.presentation.auth.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.util.EMAIL_KEY
import org.sopt.play.presentation.auth.util.PASSWORD_KEY

class RegisterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PlaySoptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RegisterRoute(
                        paddingValues = innerPadding,
                        navigateToLogin = {

                        },
                    )
                }
            }
        }
    }

    private fun onRegisterClick(
        emailText: String,
        passwordText: String,
    ) {

        val intent = Intent()
            .putExtra(EMAIL_KEY, emailText)
            .putExtra(PASSWORD_KEY, passwordText)

        setResult(RESULT_OK, intent)


        finish()
    }
}