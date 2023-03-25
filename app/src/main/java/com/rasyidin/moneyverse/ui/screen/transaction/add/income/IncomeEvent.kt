package com.rasyidin.moneyverse.ui.screen.transaction.add.income

sealed class IncomeEvent {
    data class OnNominalChange(val nominal: String): IncomeEvent()
    data class OnNotesChange(val text: String): IncomeEvent()
    data class OnSelectDate(val text: String): IncomeEvent()
    data class OnSelectCategory(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): IncomeEvent()
    data class OnSelectAccount(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): IncomeEvent()
    object Save: IncomeEvent()
    object ShowSheetCategories: IncomeEvent()
    object ShowSheetAccounts: IncomeEvent()
    object ShowSheetCalendar: IncomeEvent()
    object HideSheet: IncomeEvent()
}

sealed class SheetIncomeEvent {
    object ShowSheetCategories: SheetIncomeEvent()
    object ShowSheetAccounts: SheetIncomeEvent()
    object ShowSheetCalendar: SheetIncomeEvent()
    object Idle: SheetIncomeEvent()
}