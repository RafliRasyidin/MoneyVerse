package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome

sealed class OutcomeEvent {
    data class OnNominalChange(val text: String): OutcomeEvent()
    data class OnNotesChange(val text: String): OutcomeEvent()
    data class OnSelectDate(val text: String): OutcomeEvent()
    data class OnSelectCategory(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): OutcomeEvent()
    data class OnSelectAccount(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): OutcomeEvent()
    object Save: OutcomeEvent()
    object ShowSheetCategories: OutcomeEvent()
    object ShowSheetAccounts: OutcomeEvent()
    object ShowSheetCalendar: OutcomeEvent()
    object HideSheet: OutcomeEvent()
}

sealed class SheetOutcomeEvent {
    object ShowSheetCategories: SheetOutcomeEvent()
    object ShowSheetAccounts: SheetOutcomeEvent()
    object ShowSheetCalendar: SheetOutcomeEvent()
    object Idle: SheetOutcomeEvent()
}
