package com.example.motounplugged.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.TypeConverters
import com.example.motounplugged.database.dao.BlockedAppsDao
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.dao.SessionsDao
import com.example.motounplugged.database.entities.BlockedAppsEntity
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ProfilesEntity::class,
        SessionsEntity::class,
        BlockedAppsEntity::class
    ],
    version = 3, // ⬅️ Incrementado para refletir nova entidade
    exportSchema = false
)
@TypeConverters(Converters::class)
     abstract class MotoUnpluggedDataBase : RoomDatabase() {

    abstract fun profilesDao(): ProfilesDao
    abstract fun sessionsDao(): SessionsDao
    abstract fun blockedAppsDao(): BlockedAppsDao

    companion object {
        @Volatile
        private var INSTANCE: MotoUnpluggedDataBase? = null

        fun getDatabase(context: Context): MotoUnpluggedDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MotoUnpluggedDataBase::class.java,
                    "moto_unplugged.db"
                )
                    .addCallback(object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            CoroutineScope(Dispatchers.IO).launch {
                                // Use o instance diretamente, não getDatabase()
                                val repository = ProfileRepository(INSTANCE!!.profilesDao())
                            }
                        }
                    })
                    .fallbackToDestructiveMigration(true)
                    .build()

                INSTANCE = instance
                instance
            }
        }


    }
}
