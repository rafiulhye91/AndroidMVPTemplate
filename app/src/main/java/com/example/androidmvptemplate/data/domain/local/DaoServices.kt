package com.example.androidmvptemplate.data.domain.local

import androidx.room.*
import com.example.androidmvptemplate.data.domain.local.model.SampleDbData

@Dao
interface DaoServices {
    @Query("SELECT * FROM sampledbdata")
    suspend fun getAllSampleDbDatas(): List<SampleDbData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSampleDbData(SampleDbData: SampleDbData): Long

    @Delete
    suspend fun deleteSampleDbData(SampleDbData: SampleDbData)

    @Update
    suspend fun updateSampleDbData(SampleDbData: SampleDbData)
}