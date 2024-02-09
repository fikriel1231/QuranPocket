package com.fikrielg.quranpocket.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fikrielg.quranpocket.R
import com.fikrielg.quranpocket.data.repository.QuranRepository
import com.fikrielg.quranpocket.data.repository.QuranRepositoryImpl
import com.fikrielg.quranpocket.data.source.local.BookmarkDatabase
import com.fikrielg.quranpocket.data.source.local.QuranDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): QuranDatabase {
        return Room.databaseBuilder(
            context,
            QuranDatabase::class.java,
            "quran.db"
        ).createFromInputStream{
            context.resources.openRawResource(R.raw.qoran)
        }.build()
    }


    @Provides
    @Singleton
    fun provideBookmarkDatabase(
        @ApplicationContext context: Context
    ): BookmarkDatabase{
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
        bookmarkDatabase: BookmarkDatabase
    ):QuranRepository{
        return QuranRepositoryImpl(quranDatabase, bookmarkDatabase)
    }
}