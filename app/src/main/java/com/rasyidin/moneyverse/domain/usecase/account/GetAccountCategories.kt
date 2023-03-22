package com.rasyidin.moneyverse.domain.usecase.account

import com.rasyidin.moneyverse.data.repository.category.CategoryRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetchDataList
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.domain.model.category.CategoryType
import kotlinx.coroutines.flow.Flow

class GetAccountCategories(private val repository: CategoryRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<Category>>> {
        return fetchDataList {
            repository.getCategoriesByType(CategoryType.AddIncome.name).map { entity ->
                entity.toDomain()
            }
        }
    }
}