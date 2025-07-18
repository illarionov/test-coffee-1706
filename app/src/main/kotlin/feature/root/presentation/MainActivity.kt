package com.example.coffe1706.feature.root.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coffe1706.BuildConfig
import com.example.coffe1706.core.authmanager.AuthManager
import com.example.coffe1706.core.ui.theme3.Coffee1706Theme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authManager: AuthManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // https://issuetracker.google.com/issues/326356902
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            window.isNavigationBarContrastEnforced = false
        }

        setContent {
            val isUserLoggedIn: Boolean by authManager.isUserLoggedIn.collectAsStateWithLifecycle(
                initialValue = false,
            )

            Coffee1706Theme {
                Coffee1706RootScreen(
                    isUserLoggedIn = isUserLoggedIn,
                    modifier = Modifier.semantics {
                        testTagsAsResourceId = BuildConfig.DEBUG
                    }
                )
            }
        }
    }
}
