package com.rasyidin.moneyverse.domain.model.category

object CategoryKey {
    const val FOOD = 1
    const val SHOP = 2
    const val TRANSPORT = 3
    const val BILL = 4
    const val HEALTH = 5
    const val HOLIDAY = 6
    const val PET = 7
    const val INSURANCE = 8
    const val ALMS = 9
    const val ENTERTAIN = 10
    const val HOBBY = 11
    const val SPORT = 12
    const val SOCIAL = 13
    const val ETC_TRANSACTION_OUTCOME = 14
    const val CASH = 15
    const val ATM = 16
    const val ETC_ADD_INCOME = 30
    const val SALARY = 17
    const val BONUS = 18
    const val GIFT = 19
    const val BUSINESS = 20
    const val INVESTMENT = 21
    const val SALE = 22
    const val ETC_TRANSACTION_INCOME = 23
    const val WALLET_GREEN = 24
    const val WALLET_BLUE = 25
    const val WALLET_RED = 26
    const val WALLET_YELLOW = 27
    const val ATM_GREEN = 28
    const val ATM_YELLOW = 29
    const val ATM_RED = 30
}

enum class CategoryType {
    TransactionOutcome, TransactionIncome, AddIncome
}