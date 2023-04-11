package com.rasyidin.moneyverse.ui.screen.anggaran.add

import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType

sealed class AddAnggaranEvent {
    data class OnNominalChange(val nominal: String) : AddAnggaranEvent()
    data class OnSelectCategory(val id: Int, val iconPath: Int, val bgColor: Int, val name: String) : AddAnggaranEvent()
    data class OnSelectStartDate(val date: String) : AddAnggaranEvent()
    data class OnSelectEndDate(val date: String) : AddAnggaranEvent()
    data class OnSelectAnggaranType(val type: AnggaranType, val iconPath: Int, val bgColor: Int, val name: String) : AddAnggaranEvent()
    data class OnAnggaranBerulangChecked(val isChecked: Boolean) : AddAnggaranEvent()
    object Save : AddAnggaranEvent()
    object ShowSheetCategories : AddAnggaranEvent()
    object ShowSheetAnggaranType : AddAnggaranEvent()
    object ShowSheetCalendarStartDate : AddAnggaranEvent()
    object ShowSheetCalendarEndDate : AddAnggaranEvent()
    object HideSheet : AddAnggaranEvent()
}

sealed class SheetAnggaranEvent {
    object ShowSheetCategories : SheetAnggaranEvent()
    object ShowSheetAnggaranType : SheetAnggaranEvent()
    object ShowSheetCalendarStartDate : SheetAnggaranEvent()
    object ShowSheetCalendarEndDate : SheetAnggaranEvent()
    object Idle : SheetAnggaranEvent()
}
