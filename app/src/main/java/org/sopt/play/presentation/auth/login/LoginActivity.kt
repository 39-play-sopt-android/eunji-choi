package org.sopt.play.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.sopt.play.core.designsystem.theme.PlaySoptTheme
import org.sopt.play.presentation.auth.register.RegisterActivity
import org.sopt.play.presentation.auth.util.EMAIL_KEY
import org.sopt.play.presentation.auth.util.PASSWORD_KEY
import org.sopt.play.presentation.main.MainActivity


class LoginActivity : ComponentActivity() {
    private var resultEmail by mutableStateOf("")
    private var resultPassword by mutableStateOf("")

    private val registerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            resultEmail = result.data?.getStringExtra(EMAIL_KEY) ?: ""
            resultPassword = result.data?.getStringExtra(PASSWORD_KEY) ?: ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PlaySoptTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginRoute(
                        paddingValues = innerPadding,
                        registeredEmail = resultEmail,
                        registeredPassword = resultPassword,
                        navigateToRegister = {
                            registerLauncher.launch(Intent(this, RegisterActivity::class.java))
                        },
                        navigateToHome = {
                            val intent = Intent(this, MainActivity::class.java).apply {
                                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                            }
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }


}