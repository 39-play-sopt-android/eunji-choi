package org.sopt.play.di

import android.content.Context
import androidx.room.Room
import org.sopt.play.data.local.AppDatabase
import org.sopt.play.data.local.datasource.AuthLocalDataSource

object LocalModule {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(appContext, AppDatabase::class.java, "app_database").build()
    }

    val favoriteDao by lazy { database.favoriteDao() }

    val authLocalDataSource by lazy { AuthLocalDataSource(appContext) }
}
