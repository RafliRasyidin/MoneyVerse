package com.rasyidin.moneyverse.ui.screen.account.detail

sealed class DetailAccountEvent {
    data class OnNameChange(val text: String): DetailAccountEvent()
    data class OnDescriptionChange(val text: String?): DetailAccountEvent()
    data class OnSaldoChange(val text: String): DetailAccountEvent()
    data class OnPickAccountIcon(val iconPath: Int, val bgColor: Int): DetailAccountEvent()
    object SaveAccount: DetailAccountEvent()
}