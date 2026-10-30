package org.sopt.play.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.sopt.play.data.local.dao.FavoriteDao
import org.sopt.play.data.local.entity.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
