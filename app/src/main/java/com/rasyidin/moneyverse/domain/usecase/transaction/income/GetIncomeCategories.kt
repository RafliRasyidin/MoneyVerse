package com.rasyidin.moneyverse.domain.usecase.transaction.income

import com.rasyidin.moneyverse.data.repository.category.CategoryRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetchDataList
import com.rasyidin.moneyverse.domain.model.category.Category
import com.rasyidin.moneyverse.domain.model.category.CategoryType
import kotlinx.coroutines.flow.Flow

class GetIncomeCategories(private val categoryRepo: CategoryRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<Category>>> {
        return fetchDataList {
            categoryRepo.getCategoriesByType(CategoryType.TransactionIncome.name).map { entity ->
                entity.toDomain()
            }
        }
    }
}