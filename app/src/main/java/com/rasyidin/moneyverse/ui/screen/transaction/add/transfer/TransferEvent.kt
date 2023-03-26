package com.rasyidin.moneyverse.ui.screen.transaction.add.transfer

sealed class TransferEvent {
    data class OnNominalChange(val nominal: String) : TransferEvent()
    data class OnNotesChange(val text: String) : TransferEvent()
    data class OnSelectDate(val text: String) : TransferEvent()
    data class OnSelectFromAccount(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): TransferEvent()
    data class OnSelectToAccount(val id: Int, val iconPath: Int, val bgColor: Int, val name: String): TransferEvent()
    data class ReverseAccount(val isFromAccountSelectedFirst: Boolean) : TransferEvent()
    object Save : TransferEvent()
    object ShowSheetFromAccount : TransferEvent()
    object ShowSheetToAccount : TransferEvent()
    object ShowSheetCalendar : TransferEvent()
    object HideSheet : TransferEvent()
}

sealed class SheetTransferEvent {
    object ShowSheetFromAccount : SheetTransferEvent()
    object ShowSheetToAccount : SheetTransferEvent()
    object ShowSheetCalendar : SheetTransferEvent()
    object Idle : SheetTransferEvent()
}