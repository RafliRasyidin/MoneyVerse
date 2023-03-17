package com.rasyidin.moneyverse.data.local.entities.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategories(categories: List<CategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category WHERE categoryType = :type")
    suspend fun getCategoriesByType(type: String): List<CategoryEntity>
}