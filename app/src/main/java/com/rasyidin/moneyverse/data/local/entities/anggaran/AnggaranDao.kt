package com.rasyidin.moneyverse.data.local.entities.anggaran

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnggaranDao {

    @Query("SELECT SUM(nominalAnggaran) FROM anggaran")
    suspend fun getTotalAnggaran(): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAnggaran(anggaranEntity: AnggaranEntity)
}