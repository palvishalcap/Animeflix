package com.example.animeflix.ui.composables

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.animeflix.R
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.ui.theme.AnimeViewModel


@Composable
fun AnimeListScreen(
    onAnimeItemClicked: (animeId: Int) -> Unit,
    animeViewModel: AnimeViewModel
) {
    val animeListPaginatedItems: LazyPagingItems<AnimeEntity> =
        animeViewModel.animeMoviesState.collectAsLazyPagingItems()

    val context  = LocalContext.current

    animeListPaginatedItems.apply {
        when{
            loadState.refresh is LoadState.Loading -> {
                // show loading state
            }

            loadState.refresh is LoadState.Error -> {
                // API error on initial load (first)
                Toast.makeText(context, (loadState.refresh as LoadState.Error).error.message, Toast.LENGTH_SHORT).show()
            }

            loadState.append is LoadState.Loading -> {
                CircularProgressIndicator()
            }

            loadState.append is LoadState.Error -> {
                // API error on subsequent loads (next to first page)
                val error = loadState.append as LoadState.Error

                Toast.makeText(context, error.error.message, Toast.LENGTH_SHORT).show()
            }

        }

    }

    if (animeListPaginatedItems.itemCount == 0 && animeListPaginatedItems.loadState.isIdle) {
        Text(
            text = stringResource(id = R.string.empty_anime_list_message),
            color = Color.Gray,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier
                .padding(top = 48.dp)
        )
    } else {
        LazyVerticalGrid(modifier = Modifier, columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(animeListPaginatedItems.itemCount) { index ->
                val item = animeListPaginatedItems[index]
                item?.let {
                    AnimeListItem(it){
                        onAnimeItemClicked(it.animeId)
                    }
                }
            }
        }
    }

}