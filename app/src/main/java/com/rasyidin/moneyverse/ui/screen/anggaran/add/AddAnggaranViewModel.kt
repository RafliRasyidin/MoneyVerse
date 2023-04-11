package com.rasyidin.moneyverse.ui.screen.anggaran.add

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasyidin.moneyverse.MoneyVerseApp
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.anggaran.AddAnggaranUi
import com.rasyidin.moneyverse.domain.model.anggaran.Anggaran
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType
import com.rasyidin.moneyverse.domain.model.anggaran.ItemAnggaranType
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.domain.usecase.anggaran.add.AddAnggaranUseCase
import com.rasyidin.moneyverse.ui.theme.ColorBgBlue
import com.rasyidin.moneyverse.ui.theme.ColorBgGreen
import com.rasyidin.moneyverse.ui.theme.ColorBgPurple
import com.rasyidin.moneyverse.utils.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAnggaranViewModel @Inject constructor(private val useCase: AddAnggaranUseCase) :
    ViewModel() {

    private var _uiState = mutableStateOf(AddAnggaranUi())
    val uiState: State<AddAnggaranUi> get() = _uiState

    var buttonState by mutableStateOf(false)

    private var _sheetState: MutableState<SheetAnggaranEvent> = mutableStateOf(SheetAnggaranEvent.Idle)
    val sheetState: State<SheetAnggaranEvent> get() = _sheetState

    init {
        getCategories()
        getAnggaranType()
    }

    fun onEvent(event: AddAnggaranEvent) {
        when (event) {
            is AddAnggaranEvent.OnNominalChange -> {
                _uiState.value = uiState.value.copy(
                    nominal = event.nominal.toLongOrNull() ?: 0
                )
                setButtonValidation()
            }
            is AddAnggaranEvent.OnSelectAnggaranType -> {
                _uiState.value = uiState.value.copy(
                    anggaranType = event.type,
                    anggaranIconPath = event.iconPath,
                    anggaranBgColor = event.bgColor,
                    anggaranName = event.name
                )
            }
            is AddAnggaranEvent.OnSelectCategory -> {
                _uiState.value = uiState.value.copy(
                    categoryId = event.id,
                    categoryIconPath = event.iconPath,
                    categoryName = event.name,
                    categoryBgColor = event.bgColor
                )
                setButtonValidation()
            }
            is AddAnggaranEvent.OnSelectEndDate -> _uiState.value = uiState.value.copy(endDate = event.date)
            is AddAnggaranEvent.OnSelectStartDate -> _uiState.value = uiState.value.copy(startDate = event.date)
            is AddAnggaranEvent.OnAnggaranBerulangChecked -> _uiState.value = uiState.value.copy(anggaranBerulang = event.isChecked)
            is AddAnggaranEvent.Save -> upsertCategory()
            is AddAnggaranEvent.ShowSheetAnggaranType -> _sheetState.value = SheetAnggaranEvent.ShowSheetAnggaranType
            is AddAnggaranEvent.ShowSheetCalendarEndDate -> _sheetState.value = SheetAnggaranEvent.ShowSheetCalendarEndDate
            is AddAnggaranEvent.ShowSheetCalendarStartDate -> _sheetState.value = SheetAnggaranEvent.ShowSheetCalendarStartDate
            is AddAnggaranEvent.ShowSheetCategories -> _sheetState.value = SheetAnggaranEvent.ShowSheetCategories
            is AddAnggaranEvent.HideSheet -> _sheetState.value = SheetAnggaranEvent.Idle
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            useCase.getCategories().collect { result ->
                result.onSuccess { categories ->
                    categories?.let {
                        _uiState.value = uiState.value.copy(
                            categories = categories,
                        )
                    }
                }

            }
        }
    }

    private fun upsertCategory() {
        viewModelScope.launch {
            useCase.upsertAnggaran(
                Anggaran(
                    nominalAnggaran = uiState.value.nominal,
                    createdAt = DateUtils.getCurrentDate(),
                    categoryId = uiState.value.categoryId,
                    anggaranBerulang = if (uiState.value.anggaranBerulang) 1 else 0,
                    anggaranType = uiState.value.anggaranType,
                    startDate = uiState.value.startDate,
                    endDate = uiState.value.endDate,
                    id = uiState.value.id
                )
            )
        }
    }

    private fun getAnggaranType() {
        val anggaranTypes = mutableListOf(
            ItemAnggaranType(
                anggaranType = AnggaranType.MONTHLY,
                name = MoneyVerseApp.appContext!!.getString(R.string.bulanan),
                iconPath = R.drawable.ic_calendar_monthly,
                bgColor = ColorBgPurple.toArgb()
            ),
            ItemAnggaranType(
                anggaranType = AnggaranType.WEEKLY,
                name = MoneyVerseApp.appContext!!.getString(R.string.mingguan),
                iconPath = R.drawable.ic_calendar_weekly,
                bgColor = ColorBgGreen.toArgb()
            ),
            ItemAnggaranType(
                anggaranType = AnggaranType.ANNUAL,
                name = MoneyVerseApp.appContext!!.getString(R.string.tahunan),
                iconPath = R.drawable.ic_calendar_annual,
                bgColor = ColorBgBlue.toArgb()
            ),
        )
        val anggaran = anggaranTypes.first()
        _uiState.value = uiState.value.copy(
            anggaranTypes = anggaranTypes,
            anggaranName = anggaran.name,
            anggaranType = anggaran.anggaranType,
            anggaranBgColor = anggaran.bgColor,
            anggaranIconPath = anggaran.iconPath
        )
    }

    private fun setButtonValidation() {
        val nominal = uiState.value.nominal
        val isNominalNotEmpty = nominal != 0L && nominal.toString().isNotEmpty()
        val isCategorySelected = uiState.value.categoryId != -1
        val isStartDateSelected = uiState.value.startDate.isNotEmpty()
        val isEndDateSelected = uiState.value.endDate.isNotEmpty()
        buttonState = isNominalNotEmpty && isCategorySelected && isStartDateSelected
    }

}