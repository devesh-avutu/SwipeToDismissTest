package com.example.swipetodismisstest.ui.theme.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.swipetodismisstest.models.ProductItem

@Composable
fun ProductItem(productItem: ProductItem, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AsyncImage(
            model = productItem.image,
            contentDescription = null,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = productItem.name, color = Color.Black)
            Text(text = "SKU: ${productItem.sku}", color = Color.Black)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = productItem.quantity, color = Color.Black)
    }
}