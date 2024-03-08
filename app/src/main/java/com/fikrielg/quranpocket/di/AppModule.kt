package com.fikrielg.quranpocket.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.repository.QuranRepositoryImpl
import com.fikrielg.quranpocket.data.source.local.BookmarkDatabase
import com.fikrielg.quranpocket.data.source.local.QuranDatabase
import com.fikrielg.quranpocket.data.source.remote.service.ApiInterface
import com.fikrielg.quranpocket.service.MyPlayerService
import com.fikrielg.quranpocket.service.location.LocationClient
import com.fikrielg.quranpocket.service.location.LocationClientImpl
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import snow.player.PlayerClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSnowPlayer(
        @ApplicationContext context: Context
    ): PlayerClient {
        return PlayerClient.newInstance(context, MyPlayerService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): QuranDatabase {
        return Room.databaseBuilder(
            context,
            QuranDatabase::class.java,
            "quran.db"
        ).createFromInputStream {
            context.resources.openRawResource(R.raw.qoran)
        }
            .build()
    }


    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase {
        return Room.databaseBuilder(
            context,
            BookmarkDatabase::class.java,
            "bookmark.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRepository(
        quranDatabase: QuranDatabase,
        api: ApiInterface,
        bookmarkDatabase: BookmarkDatabase
    ): QuranRepository {
        return QuranRepositoryImpl(quranDatabase, api, bookmarkDatabase)
    }

    @Provides
    @Singleton
    fun provideLocationClient(
        app: Application,
        coroutineScope: CoroutineScope
    ): LocationClient {
        return LocationClientImpl(
            app,
            LocationServices.getFusedLocationProviderClient(app.applicationContext),
            coroutineScope
        )
    }

    @Provides
    @Singleton
    fun provideApi(
        @ApplicationContext applicationContext: Context
    ): ApiInterface {
        return Retrofit.Builder()
            .baseUrl("https://prayer-times-xi.vercel.app/api/prayer/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope() : CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }
}