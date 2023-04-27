package com.rasyidin.moneyverse.ui.screen.anggaran.add

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rasyidin.moneyverse.R
import com.rasyidin.moneyverse.domain.model.anggaran.AddAnggaranUi
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.ANNUAL
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.DAILY
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.MONTHLY
import com.rasyidin.moneyverse.domain.model.anggaran.AnggaranType.WEEKLY
import com.rasyidin.moneyverse.ui.component.MVButtonPrimary
import com.rasyidin.moneyverse.ui.component.MVCalendarVerticalPager
import com.rasyidin.moneyverse.ui.component.MVCardSelect
import com.rasyidin.moneyverse.ui.component.MVTextFieldNominal
import com.rasyidin.moneyverse.ui.component.MVToolbar
import com.rasyidin.moneyverse.ui.component.SheetContentAnggaranType
import com.rasyidin.moneyverse.ui.component.SheetContentCalendar
import com.rasyidin.moneyverse.ui.component.SheetContentCategories
import com.rasyidin.moneyverse.ui.theme.ColorGray200
import com.rasyidin.moneyverse.ui.theme.ColorGray500
import com.rasyidin.moneyverse.ui.theme.ColorPurple500
import com.rasyidin.moneyverse.ui.theme.ColorWhite
import com.rasyidin.moneyverse.ui.theme.MoneyVerseTheme
import com.rasyidin.moneyverse.ui.theme.SheetShape
import com.rasyidin.moneyverse.utils.DateUtils
import com.rasyidin.moneyverse.utils.DateUtils.formatDate
import com.rasyidin.moneyverse.utils.highlight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun AddAnggaranScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AddAnggaranViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.value
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetShape = SheetShape,
        sheetContent = {
            when (viewModel.sheetState.value) {
                SheetAnggaranEvent.Idle -> {}
                SheetAnggaranEvent.ShowSheetAnggaranType -> {
                    SheetContentAnggaranType(
                        items = uiState.anggaranTypes,
                        onCloseClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                            }
                        },
                        onItemClick = { item ->
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(
                                    AddAnggaranEvent.OnSelectAnggaranType(
                                        type = item.anggaranType,
                                        iconPath = item.iconPath,
                                        bgColor = item.bgColor,
                                        name = item.name
                                    )
                                )
                            }
                        }
                    )
                }
                SheetAnggaranEvent.ShowSheetCalendarEndDate -> {
                    SheetContentCalendar(
                        onSelectedDateClick = { value ->
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(AddAnggaranEvent.OnSelectEndDate(value))
                            }
                        }
                    )
                }
                SheetAnggaranEvent.ShowSheetCalendarStartDate -> {
                    when (uiState.anggaranType) {
                        DAILY -> {
                            SheetContentCalendar(
                                onSelectedDateClick = { value ->
                                    coroutineScope.launch {
                                        modalSheetState.hide()
                                        viewModel.onEvent(AddAnggaranEvent.OnSelectStartDate(value))
                                    }
                                }
                            )
                        }
                        WEEKLY -> MVCalendarVerticalPager(onSelected = {})
                        MONTHLY -> {
                            MVCalendarVerticalPager(
                                modifier = Modifier.fillMaxHeight(.5F),
                                onSelected = {}
                            )
                        }
                        ANNUAL -> MVCalendarVerticalPager(onSelected = {})
                    }
                }
                SheetAnggaranEvent.ShowSheetCategories -> {
                    SheetContentCategories(
                        title = stringResource(id = R.string.pilih_kategori),
                        categories = uiState.categories,
                        isShowName = true,
                        onItemClick = { category ->
                            coroutineScope.launch {
                                viewModel.onEvent(
                                    AddAnggaranEvent.OnSelectCategory(
                                        id = category.id,
                                        iconPath = category.iconPath,
                                        bgColor = category.bgColor,
                                        name = category.name
                                    )
                                )
                                modalSheetState.hide()
                                viewModel.onEvent(AddAnggaranEvent.HideSheet)
                            }
                        },
                        onCloseClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                viewModel.onEvent(AddAnggaranEvent.HideSheet)
                            }
                        }
                    )
                }
            }
        },
        content = {
            AddAnggaranContent(
                modifier = modifier,
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onNominalChange = { nominal ->
                    viewModel.onEvent(AddAnggaranEvent.OnNominalChange(nominal))
                },
                onCardCategoryClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(AddAnggaranEvent.ShowSheetCategories)
                    coroutineScope.launch {
                        modalSheetState.show()
                    }
                },
                onCardAnggaranTypeClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(AddAnggaranEvent.ShowSheetAnggaranType)
                    coroutineScope.launch {
                        modalSheetState.show()
                    }
                },
                onCardStartDateClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(AddAnggaranEvent.ShowSheetCalendarStartDate)
                    coroutineScope.launch {
                        modalSheetState.show()
                    }
                },
                onCardEndDateClick = {
                    keyboardController?.hide()
                    viewModel.onEvent(AddAnggaranEvent.ShowSheetCalendarEndDate)
                    coroutineScope.launch {
                        modalSheetState.show()
                    }
                },
                onCheckedAnggaranBerulang = { isChecked ->
                    viewModel.onEvent(AddAnggaranEvent.OnAnggaranBerulangChecked(isChecked))
                },
                onSaveClick = {
                    viewModel.onEvent(AddAnggaranEvent.Save)
                }
            )
        }
    )
}

@Composable
fun AddAnggaranContent(
    modifier: Modifier = Modifier,
    uiState: AddAnggaranUi,
    onBackClick: () -> Unit,
    onNominalChange: (String) -> Unit,
    onCardCategoryClick: () -> Unit,
    onCardAnggaranTypeClick: () -> Unit,
    onCardStartDateClick: () -> Unit,
    onCardEndDateClick: () -> Unit,
    onCheckedAnggaranBerulang: (Boolean) -> Unit,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 12.dp)
    ) {
        MVToolbar(
            title = stringResource(id = R.string.buat_anggaran),
            onBackClick = onBackClick
        )
        MVTextFieldNominal(
            nominal = uiState.nominal.toString(),
            onNominalChange = onNominalChange
        )
        Spacer(modifier = Modifier.height(20.dp))
        MVCardSelect(
            label = stringResource(id = R.string.kategori),
            name = uiState.categoryName,
            bgColor = uiState.categoryBgColor,
            iconPath = uiState.categoryIconPath,
            onClick = onCardCategoryClick
        )
        Spacer(modifier = Modifier.height(18.dp))
        MVCardSelect(
            label = stringResource(id = R.string.jenis_anggaran),
            name = uiState.anggaranName,
            bgColor = uiState.anggaranBgColor,
            iconPath = uiState.anggaranIconPath,
            onClick = onCardAnggaranTypeClick
        )
        Spacer(modifier = Modifier.height(18.dp))
        MVCardSelect(
            label = stringResource(
                id = when {
                    uiState.anggaranBerulang -> {
                        when (uiState.anggaranType) {
                            DAILY -> R.string.dari_tanggal
                            WEEKLY -> R.string.dari_tanggal
                            MONTHLY -> R.string.dari_bulan
                            ANNUAL -> R.string.dari_tahun
                        }
                    }
                    else -> R.string.tanggal
                }
            ),
            name = uiState.startDate.formatDate(to = DateUtils.NORMAL_DATE_FORMAT),
            bgColor = null,
            iconPath = R.drawable.ic_calendar,
            onClick = onCardStartDateClick
        )
        Spacer(modifier = Modifier.height(18.dp))
        AnimatedVisibility(visible = uiState.anggaranBerulang) {
            MVCardSelect(
                label = stringResource(
                    id = when (uiState.anggaranType) {
                        DAILY -> R.string.sampai_tanggal
                        WEEKLY -> R.string.sampai_tanggal
                        MONTHLY -> R.string.sampai_bulan
                        ANNUAL -> R.string.sampai_tahun
                    }
                ),
                name = uiState.endDate.formatDate(to = DateUtils.NORMAL_DATE_FORMAT),
                bgColor = null,
                iconPath = R.drawable.ic_calendar,
                onClick = onCardEndDateClick
            )
        }
        AnimatedVisibility(visible = uiState.anggaranBerulang) {
            Spacer(modifier = Modifier.height(18.dp))
        }
        InfoAnggaranBerulang(
            startDate = uiState.startDate,
            endDate = uiState.endDate,
            anggaranType = uiState.anggaranType,
            anggaranBerulang = uiState.anggaranBerulang
        )
        Spacer(modifier = Modifier.height(6.dp))
        CheckBoxAnggaranBerulang(
            isChecked = uiState.anggaranBerulang,
            onCheckedChange = onCheckedAnggaranBerulang
        )
        Spacer(modifier = Modifier.weight(1F))
        MVButtonPrimary(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            onClick = { onSaveClick.invoke() },
            enabled = uiState.buttonState,
        ) {
            Text(
                text = stringResource(id = R.string.simpan),
                style = MaterialTheme.typography.button,
                color = ColorWhite
            )
        }
    }
}

@Composable
fun CheckBoxAnggaranBerulang(
    modifier: Modifier = Modifier,
    isChecked: Boolean = false,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = ColorPurple500,
                uncheckedColor = ColorGray500
            )
        )
        Text(
            text = stringResource(id = R.string.anggaran_berulang),
            style = MaterialTheme.typography.h6,
            color = ColorGray500
        )
    }
}

@Composable
fun InfoAnggaranBerulang(
    modifier: Modifier = Modifier,
    anggaranBerulang: Boolean,
    anggaranType: AnggaranType,
    startDate: String,
    endDate: String?
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 0.dp,
        border = BorderStroke(1.dp, ColorGray200)
    ) {
        val annual = stringResource(id = R.string.tahunan).lowercase()
        val weekly = stringResource(id = R.string.mingguan).lowercase()
        val monthly = stringResource(id = R.string.bulanan).lowercase()
        val daily = stringResource(id = R.string.harian).lowercase()
        val startDateFormatted = startDate.formatDate(to = DateUtils.NORMAL_DATE_FORMAT)
        val endDateFormatted = endDate?.formatDate(to = DateUtils.NORMAL_DATE_FORMAT)
        val infoAnggaran = when (anggaranType) {
            DAILY -> {
                if (anggaranBerulang) stringResource(
                    id = R.string.info_anggaran_berulang,
                    daily,
                    startDateFormatted,
                    endDateFormatted!!
                ) else stringResource(
                    id = R.string.info_anggaran,
                    daily,
                    startDateFormatted
                )
            }
            WEEKLY -> {
                if (anggaranBerulang) stringResource(
                    id = R.string.info_anggaran_berulang,
                    weekly,
                    startDateFormatted,
                    endDateFormatted!!
                ) else stringResource(
                    id = R.string.info_anggaran,
                    weekly,
                    startDateFormatted
                )
            }
            MONTHLY -> {
                if (anggaranBerulang) stringResource(
                    id = R.string.info_anggaran_berulang,
                    monthly,
                    startDateFormatted,
                    endDateFormatted!!
                ) else stringResource(
                    id = R.string.info_anggaran,
                    monthly,
                    startDateFormatted
                )
            }
            ANNUAL -> {
                if (anggaranBerulang) stringResource(
                    id = R.string.info_anggaran_berulang,
                    annual,
                    startDateFormatted,
                    endDateFormatted!!
                ) else stringResource(
                    id = R.string.info_anggaran,
                    annual,
                    startDateFormatted
                )
            }
        }
        Text(
            modifier = Modifier.padding(12.dp),
            text = when (anggaranType) {
                DAILY -> highlightInfoAnggaran(
                    infoAnggaran = infoAnggaran,
                    isAnggaranBerulang = anggaranBerulang,
                    anggaranType = daily,
                    startDate = startDateFormatted,
                    endDate = endDateFormatted
                )
                WEEKLY -> highlightInfoAnggaran(
                    infoAnggaran = infoAnggaran,
                    isAnggaranBerulang = anggaranBerulang,
                    anggaranType = weekly,
                    startDate = startDateFormatted,
                    endDate = endDateFormatted
                )
                MONTHLY -> highlightInfoAnggaran(
                    infoAnggaran = infoAnggaran,
                    isAnggaranBerulang = anggaranBerulang,
                    anggaranType = monthly,
                    startDate = startDateFormatted,
                    endDate = endDateFormatted
                )
                ANNUAL -> highlightInfoAnggaran(
                    infoAnggaran = infoAnggaran,
                    isAnggaranBerulang = anggaranBerulang,
                    anggaranType = annual,
                    startDate = startDateFormatted,
                    endDate = endDateFormatted
                )
            },
            style = MaterialTheme.typography.subtitle1,
            color = ColorGray500
        )
    }
}

@Composable
private fun highlightInfoAnggaran(infoAnggaran: String, isAnggaranBerulang: Boolean, anggaranType: String, startDate: String, endDate: String?): AnnotatedString {
    return if (isAnggaranBerulang) {
        infoAnggaran.highlight(MaterialTheme.typography.h6, anggaranType, startDate, endDate!!, color = ColorPurple500)
    } else {
        infoAnggaran.highlight(MaterialTheme.typography.h6, anggaranType, startDate, color = ColorPurple500)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddAnggaranContent() {
    MoneyVerseTheme {
        AddAnggaranContent(
            uiState = AddAnggaranUi(anggaranBerulang = true),
            onBackClick = {},
            onNominalChange = {},
            onCardCategoryClick = {},
            onCardAnggaranTypeClick = {},
            onCardStartDateClick = {},
            onCardEndDateClick = {},
            onCheckedAnggaranBerulang = {},
            onSaveClick = {}
        )
    }
}
