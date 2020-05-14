package com.lucas.frame2.data

import android.app.Application
import androidx.room.Room
import com.lucas.frame2.data.db.AppDatabase
import com.lucas.frame2.data.remote.ApiServer
import org.koin.dsl.module
import retrofit2.Retrofit

val netModule = module {
    //提供server
    single { get<Retrofit>().create(ApiServer::class.java) }
}

val localModule = module {
    //提供room数据库
    single {
        val db: AppDatabase =
            Room.databaseBuilder(get<Application>(), AppDatabase::class.java, "db_file")
                .addMigrations().build()
        db
    }
}
val appModules = listOf(netModule, localModule)