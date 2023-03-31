package com.rasyidin.moneyverse.ui.screen.transaction.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.palette.graphics.Palette
import com.rasyidin.moneyverse.domain.model.transaction.DetailTransactionUi
import com.rasyidin.moneyverse.domain.onSuccess
import com.rasyidin.moneyverse.domain.usecase.transaction.detail.DetailTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailTransactionViewModel @Inject constructor(
    private val useCase: DetailTransactionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _uiState = mutableStateOf(DetailTransactionUi())
    val uiState: State<DetailTransactionUi> = _uiState

    init {
        savedStateHandle.get<Int>("transactionId")?.let { transactionId ->
            viewModelScope.launch {
                useCase.getDetailTransaction(transactionId).collect { result ->
                    result.onSuccess { detailTransaction ->
                        detailTransaction?.let {
                            _uiState.value = uiState.value.copy(
                                id = detailTransaction.id,
                                nominal = detailTransaction.nominal,
                                createdAt = detailTransaction.createdAt,
                                notes = detailTransaction.notes,
                                transactionType = detailTransaction.transactionType,
                                categoryId = detailTransaction.categoryId,
                                fromAccountId = detailTransaction.fromAccountId,
                                toAccountId = detailTransaction.toAccountId,
                                accountName = detailTransaction.accountName,
                                accountIconPath = detailTransaction.accountIconPath,
                                accountBgColor = detailTransaction.accountBgColor,
                                categoryName = detailTransaction.categoryName,
                                categoryIconPath = detailTransaction.categoryIconPath,
                                categoryBgColor = detailTransaction.categoryBgColor
                            )
                        }
                    }
                }
            }
        }
    }

    fun findDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
        val bitmapDrawable = drawable.toBitmap()
        val bitmap = bitmapDrawable.copy(
            Bitmap.Config.ARGB_8888, true
        )
        Palette.from(bitmap).generate { palette ->
            palette?.dominantSwatch?.rgb?.let { colorValue ->
                onFinish(Color(colorValue))
            }
        }
    }
}