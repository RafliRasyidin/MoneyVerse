package com.rasyidin.moneyverse.domain.model.anggaran

data class AnggaranUi(
    var id: String = "",
    var sisaAnggaran: Long = 0,
    var totalAnggaran: Long = 0,
    var dateFilter: String = "",
    var listAnggaran: List<ItemAnggaran> = emptyList()
)
