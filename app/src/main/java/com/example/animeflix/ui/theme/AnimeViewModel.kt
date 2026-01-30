package com.example.animeflix.ui.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.animeflix.data.db.AnimeEntity
import com.example.animeflix.data.db.AnimeWithGenres
import com.example.animeflix.data.db.CastWithCharacter
import com.example.animeflix.domain.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    val animeMoviesState: Flow<PagingData<AnimeEntity>> =
        animeRepository.animeListPager().cachedIn(viewModelScope)

    fun observeAnime(animeId: Int): Flow<AnimeWithGenres?> = animeRepository.observeAnime(animeId)

    fun observeCast(animeId: Int): Flow<List<CastWithCharacter>> = animeRepository.observeCast(animeId)

    fun refreshAnimeDetail(animeId: Int) {
        viewModelScope.launch {
            animeRepository.refreshAnimeDetail(animeId)
        }
    }

    fun refreshAnimeCast(animeId: Int) {
        viewModelScope.launch {
            animeRepository.refreshAnimeCast(animeId)
        }
    }

}