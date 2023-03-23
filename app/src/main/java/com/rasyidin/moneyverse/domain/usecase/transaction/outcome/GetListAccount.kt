package com.rasyidin.moneyverse.domain.usecase.transaction.outcome

import com.rasyidin.moneyverse.data.repository.account.AccountRepository
import com.rasyidin.moneyverse.domain.ResultState
import com.rasyidin.moneyverse.domain.fetchDataList
import com.rasyidin.moneyverse.domain.model.category.Category
import kotlinx.coroutines.flow.Flow

class GetListAccount(private val accountRepo: AccountRepository) {

    suspend operator fun invoke(): Flow<ResultState<List<Category>>> {
        return fetchDataList {
            accountRepo.getListAccount().map { entity ->
                Category(
                    id = entity.id,
                    bgColor = entity.bgColor,
                    iconPath = entity.iconPath,
                    name = entity.name
                )
            }
        }
    }
}