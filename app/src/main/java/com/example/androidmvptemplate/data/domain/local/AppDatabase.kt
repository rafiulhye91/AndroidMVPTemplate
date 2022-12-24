package com.example.androidmvptemplate.data.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.androidmvptemplate.data.domain.local.model.SampleDbData

@Database(
    entities = [SampleDbData::class],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getDaoServices(): DaoServices

    companion object {
        const val DATABASE_NAME = "sample_db"
    }
}