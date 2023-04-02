package com.rasyidin.moneyverse.ui.screen.transaction.detail

sealed class DetailTransactionEvent {
    object DeleteTransaction : DetailTransactionEvent()
}
