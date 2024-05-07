package com.example.swipetodismisstest

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swipetodismisstest.models.ProductItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val productItems = MutableStateFlow(
        listOf(
            ProductItem(
                "1",
                "Coke",
                "https://w7.pngwing.com/pngs/519/631/png-transparent-coca-cola-diet-coke-fizzy-drinks-take-out-coca-cola-7-up-takeout.png",
                "000000",
                "1"
            ),
            ProductItem(
                "2",
                "Pepsi",
                "https://m.media-amazon.com/images/I/51-r9pOh08L.jpg",
                "000001",
                "2"
            ),
            ProductItem(
                "3",
                "Fanta",
                "https://www.bigbasket.com/media/uploads/p/xxl/251019_8-fanta-soft-drink-orange-flavoured.jpg",
                "000002",
                "3"
            )
        )
    )

    private val _scannedItem: MutableSharedFlow<String> = MutableSharedFlow()

    val scannedItem: SharedFlow<String> = _scannedItem

    fun onBarcodeScanned(code: String) {
        val items = productItems.value.toMutableList()
        val scannedItem = items.firstOrNull { it.sku == code }
        if (scannedItem != null) {
            viewModelScope.launch {
                Log.d("barcodeProduct", scannedItem.id)
                _scannedItem.emit(scannedItem.id)
            }
        }
    }

    fun onItemPicked(itemId: String) {
        val items = productItems.value.toMutableList()
        val pickedItem = items.firstOrNull { it.id == itemId }
        if (pickedItem != null) {
            items.remove(pickedItem)
            productItems.value = items
        }
    }
}

sealed class Action {
    data class SwipeToPick(val itemId: String) : Action()
}