package com.example.animeflix.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Dimension
import coil.size.Size
import com.example.animeflix.R
import com.example.animeflix.data.db.AnimeEntity


@Composable
fun AnimeListItem(
    animeData: AnimeEntity,
    onItemClicked: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            // Poster
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(animeData.imageUrl)
                        .crossfade(true)
                        .placeholder(R.drawable.loading)
                        .error(R.drawable.loading)
                        .build()
                ),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f), // poster-like ratio
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(12.dp)
            ) {

                // Rating + Episodes row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = animeData.score?.toString() ?: "-",
                        style = MaterialTheme.typography.labelMedium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "${animeData.episodes ?: 0} episodes",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Title
                Text(
                    text = animeData.title,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
@Composable
fun AnimeListItem(animeData: AnimeEntity, onItemClicked: ()-> Unit){

    Column(
        modifier = Modifier.shadow(elevation = 1.dp).clip(RoundedCornerShape(8.dp)).wrapContentHeight().clickable {
            onItemClicked()
        },
    ){
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .crossfade(true)
                    .data(animeData.imageUrl)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .size(Size.ORIGINAL)
                    .scale(coil.size.Scale.FILL)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 4.dp)
                .fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.Fit
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(0xFFFFC107),
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = animeData.score?.toString() ?: "-",
                style = MaterialTheme.typography.labelMedium
            )

            // Episodes count
            Text(
                text = "${animeData.episodes ?: 0} Episodes",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.End
            )

        }


        Text(
            text = animeData.title,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

 **/