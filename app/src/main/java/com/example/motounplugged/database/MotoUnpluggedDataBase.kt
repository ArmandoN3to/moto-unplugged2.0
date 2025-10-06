package com.example.motounplugged.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.dao.SessionsDao
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.database.entities.SessionsEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        ProfilesEntity::class,
        SessionsEntity::class
    ],
    version = 2, // ⬅️ Incrementado para refletir nova entidade
    exportSchema = false
)
abstract class MotoUnpluggedDataBase : RoomDatabase() {

    abstract fun profilesDao(): ProfilesDao
    abstract fun sessionsDao(): SessionsDao

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

                                // Perfis padrão
                                repository.save(ProfilesEntity(ProfileName = "Trabalho", appCount = 4))
                                repository.save(ProfilesEntity(ProfileName = "Estudo", appCount = 4))
                                repository.save(ProfilesEntity(ProfileName = "Academia", appCount = 3))
                            }
                        }
                    })
                    .fallbackToDestructiveMigration(true) // ❌ Remove o parâmetro `true`, não é necessário
                    .build()

                INSTANCE = instance
                instance
            }
        }


    }
}
