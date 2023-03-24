package com.rasyidin.moneyverse.ui.screen.transaction.add.outcome

sealed class OutcomeTransactionEvent {
    data class OnNominalChange(val text: String): OutcomeTransactionEvent()
    data class OnNotesChange(val text: String): OutcomeTransactionEvent()
    data class OnSelectDate(val text: String): OutcomeTransactionEvent()
    data class OnSelectCategory(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): OutcomeTransactionEvent()
    data class OnSelectAccount(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): OutcomeTransactionEvent()
    object SaveTransaction: OutcomeTransactionEvent()
    object ShowSheetCategories: OutcomeTransactionEvent()
    object ShowSheetAccounts: OutcomeTransactionEvent()
    object ShowSheetCalendar: OutcomeTransactionEvent()
    object HideSheet: OutcomeTransactionEvent()
}

sealed class SheetOutcomeTransactionEvent {
    object ShowSheetCategories: SheetOutcomeTransactionEvent()
    object ShowSheetAccounts: SheetOutcomeTransactionEvent()
    object ShowSheetCalendar: SheetOutcomeTransactionEvent()
    object Idle: SheetOutcomeTransactionEvent()
}
