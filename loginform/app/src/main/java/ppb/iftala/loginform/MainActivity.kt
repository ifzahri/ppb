package ppb.iftala.loginform

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ppb.iftala.loginform.ui.theme.LoginScreen
import ppb.iftala.loginform.ui.theme.LoginformTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginformTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(
                        onLoginClick = { email, password ->
                            performLogin(email, password)
                        },
                        onForgotPasswordClick = {
                            navigateToForgotPassword()
                        }
                    )
                }
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        // For demonstration purposes, just show a toast
        Toast.makeText(
            this,
            "Login attempt with: $email",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun navigateToForgotPassword() {
        // Handle navigation to forgot password screen
        Toast.makeText(
            this,
            "Navigating to forgot password",
            Toast.LENGTH_SHORT
        ).show()
    }
}