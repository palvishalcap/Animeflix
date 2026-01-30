package com.example.animeflix.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.animeflix.ui.theme.AnimeViewModel

@Composable
fun AnimeDetailScreen(animeId: Int, viewModel: AnimeViewModel) {

    LaunchedEffect(animeId) {
        viewModel.refreshAnimeDetail(animeId)
        viewModel.refreshAnimeCast(animeId)
    }

    val animeWithGenres by viewModel.observeAnime(animeId).collectAsState(initial = null)
    val castList by viewModel.observeCast(animeId).collectAsState(initial = emptyList())


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        val anime = animeWithGenres?.anime
        if (anime == null) {
            item {
                Text(
                    text = "Loading...",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        } else {

            val youtubeId = anime.trailerYoutubeId
            if (!youtubeId.isNullOrBlank()) {
                item {
                    AnimeVideoPlayer(
                        videoUrl = youtubeId,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = anime.title,
                        style = MaterialTheme.typography.headlineSmall,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    val genres = animeWithGenres?.genres.orEmpty()
                    if (genres.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            genres.forEach { genre ->
                                Surface(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        text = genre.name,
                                        modifier = Modifier.padding(
                                            horizontal = 10.dp,
                                            vertical = 6.dp
                                        ),
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }
                            }
                        }
                    }

                    val score = anime.score?.toFloat() ?: 0f
                    val episodes = anime.episodes ?: 0
                    if (score > 0f || episodes > 0) {
                        Row(modifier = Modifier.padding(top = 10.dp)) {
                            AnimeMetaInfoDetail(
                                rating = score,
                                episodeCount = episodes
                            )
                        }
                    }
                }
            }

            val synopsis = anime.synopsis
            if (!synopsis.isNullOrBlank()) {
                item {
                    Text(
                        text = synopsis,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                Text(
                    text = "Cast",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            if (castList.isEmpty()) {
                item {
                    Text(
                        text = "Loading cast...",
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            } else {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(castList.size) { index ->
                            CastCard(cast = castList[index])
                        }
                    }
                }
            }
        }
    }

}