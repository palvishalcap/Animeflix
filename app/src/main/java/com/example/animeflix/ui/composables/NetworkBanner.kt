package com.example.animeflix.ui.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.animeflix.data.model.NetworkStatus

@Composable
fun NetworkBanner(networkStatus: NetworkStatus) {
    Log.d("Anime-network","network state : ${networkStatus.toString()} ")
    if (networkStatus is NetworkStatus.Unavailable) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue.copy(alpha = 0.5f))
                .padding(top = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No internet connection!",
                color = Color.White,
                fontSize = 12.sp
            )
        }
    }
}