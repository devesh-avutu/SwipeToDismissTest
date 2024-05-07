package com.example.swipetodismisstest.ui.theme.composables

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FixedThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.swipetodismisstest.models.ProductItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun OrderProductItemsList(
    productItems: List<ProductItem>,
    scannedItemId: State<String?>,
    onItemPicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(productItems.size, key = { index -> productItems[index].id }) { index ->
            val productItem = productItems[index]
            val dismissState = rememberDismissState()
            SwipeToDismiss(state = dismissState,
                background = {
                    ProductItemSwipeOptions(dismissState)
                },
                dismissThresholds = { FixedThreshold(210.dp) }
            ) {
                LaunchedEffect(key1 = dismissState) {
                    snapshotFlow { dismissState.currentValue }
                        .collect { currentValue ->
                            if (currentValue == DismissValue.DismissedToEnd) {
                                onItemPicked(productItem.id)
                                dismissState.reset()
                            } else if (currentValue == DismissValue.DismissedToStart) {
                                dismissState.reset()
                            }
                        }
                }
                LaunchedEffect(key1 = scannedItemId) {
                    snapshotFlow { scannedItemId.value }
                        .collect { itemId ->
                            if (itemId == productItem.id) {
                                Log.d("barcodeUI", "dismissing scanned item ${productItem.id}")
                                dismissState.animateTo(DismissValue.DismissedToEnd)
                                dismissState.currentValue
                            }
                        }
                }
                ProductItem(productItem = productItem)
            }
        }
    }
}