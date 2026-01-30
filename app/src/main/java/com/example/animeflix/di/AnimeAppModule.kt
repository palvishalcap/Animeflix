package com.example.animeflix.di

import android.content.Context
import androidx.room.Room
import com.example.animeflix.data.api.AnimeApiClient
import com.example.animeflix.data.datasource.AnimeRepoImpl
import com.example.animeflix.data.db.AnimeDatabase
import com.example.animeflix.domain.AnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnimeAppModule {

    @Singleton
    @Provides
    fun provideBaseURL(): String {
        return "https://api.jikan.moe/"
    }

    @Singleton
    @Provides
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okhttpClient: OkHttpClient, baseURL: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).client(okhttpClient).build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): AnimeApiClient {
        return retrofit.create(AnimeApiClient::class.java)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AnimeDatabase {
        return Room.databaseBuilder(
            context,
            AnimeDatabase::class.java,
            "animeflix.db",
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun providesAnimeRepository(animeApiClient: AnimeApiClient, db: AnimeDatabase): AnimeRepository {
        return AnimeRepoImpl(animeApiClient, db)
    }


}