package com.rasyidin.moneyverse.data.repository.category

import com.rasyidin.moneyverse.data.local.entities.category.CategoryDao
import com.rasyidin.moneyverse.data.local.entities.category.CategoryEntity
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(private val dao: CategoryDao) : CategoryRepository {
    override suspend fun getCategoriesByType(type: String): List<CategoryEntity> {
        return dao.getCategoriesByType(type)
    }
}