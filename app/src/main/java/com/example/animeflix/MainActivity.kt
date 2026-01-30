package com.example.animeflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.animeflix.data.model.NetworkStatus
import com.example.animeflix.ui.composables.BaseAppScreen
import com.example.animeflix.ui.composables.NetworkBanner
import com.example.animeflix.ui.navigation.AnimeNavHost
import com.example.animeflix.ui.theme.AnimeViewModel
import com.example.animeflix.ui.theme.AnimeflixTheme
import com.example.animeflix.utils.NetworkChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewmodel by viewModels<AnimeViewModel>()

    private val networkChecker by lazy {
        NetworkChecker(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val networkStatus by networkChecker
                .networkStatus
                .collectAsStateWithLifecycle()

            AnimeflixTheme {
                BaseAppScreen(networkChecker) {

                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            NetworkBanner(networkStatus)
                        }
                    ) { innerPadding ->
                        AnimeNavHost(
                            navController = navController,
                            modifier = Modifier.padding(innerPadding),
                            viewModel = viewmodel
                        )
                    }
                }
            }
        }
    }
}
