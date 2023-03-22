package com.rasyidin.moneyverse.data.repository.category

import com.rasyidin.moneyverse.data.local.entities.category.CategoryEntity

interface CategoryRepository {

    suspend fun getCategoriesByType(type: String): List<CategoryEntity>
}