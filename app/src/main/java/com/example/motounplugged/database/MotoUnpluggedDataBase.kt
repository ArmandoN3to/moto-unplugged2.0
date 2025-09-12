package com.example.motounplugged.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.motounplugged.database.dao.ProfilesDao
import com.example.motounplugged.database.entities.ProfilesEntity
import com.example.motounplugged.repositories.ProfileRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//database vai guardar as infomações, precisa passar as entidades e a lista que tem a entidade fazendo referência da class dela e a versão
@Database(entities = [ProfilesEntity::class],
    version = 1)
abstract class MotoUnpluggedDataBase: RoomDatabase() {

    abstract fun profilesDao(): ProfilesDao // cria uma função para retornar o DAO da entidade, é obrigatório retornar o DAO
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
                                val database = getDatabase(context) // pega o singleton
                                val repository = ProfileRepository(database.profilesDao())

                                // Inserir perfis pré-definidos
                                repository.save(ProfilesEntity(ProfileName = "Trabalho", appCount = 4))
                                repository.save(ProfilesEntity(ProfileName = "Estudo", appCount = 4))
                                repository.save(ProfilesEntity(ProfileName = "Academia", appCount = 3))
                            }
                        }
                    })
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }

}

// ksp faz a comunicação com o banco de dados e permite que o room salve as informações