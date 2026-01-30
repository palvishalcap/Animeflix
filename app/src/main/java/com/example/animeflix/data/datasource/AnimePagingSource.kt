package com.example.animeflix.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.animeflix.data.api.AnimeApiClient
import com.example.animeflix.data.model.AnimeListDataResponse
import com.example.animeflix.utils.AnimeUtils
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AnimePagingSource @Inject constructor(
    private val animeApiClient: AnimeApiClient,
) : PagingSource<Int, AnimeListDataResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AnimeListDataResponse> {
        return try {
            val currentPage = params.key ?: 1
            val response = getResult { animeApiClient.getAnimeList(currentPage, AnimeUtils.ANIME_LIST_PAGE_SIZE) }
            println("response $response")
            when (response) {
                is ApiResult.Success -> {
                    val movieList = response.data?.data
                    val nextPage =
                        if (movieList.isNullOrEmpty()) null else currentPage+1
                    LoadResult.Page(
                        data = movieList ?: emptyList(),
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = nextPage
                    )
                }

                is ApiResult.Error<*> -> {
                    LoadResult.Error(response.error)
                }

                is ApiResult.Loading -> TODO()
            }
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AnimeListDataResponse>): Int? {
        return state.anchorPosition
    }

}
