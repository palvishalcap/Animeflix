package com.example.animeflix.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.example.animeflix.utils.NetworkChecker

@Composable
fun BaseAppScreen(
    networkChecker: NetworkChecker,
    content: @Composable () -> Unit
) {
    DisposableEffect(Unit) {
        networkChecker.register()

        onDispose {
            networkChecker.unregister()
        }
    }

    content()
}
