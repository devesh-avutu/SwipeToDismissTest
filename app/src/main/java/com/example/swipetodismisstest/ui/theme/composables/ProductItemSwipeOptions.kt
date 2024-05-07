package com.example.swipetodismisstest.ui.theme.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProductItemSwipeOptions(dismissState: DismissState, modifier: Modifier = Modifier) {
    val direction = dismissState.dismissDirection
    val color = when (direction) {
        DismissDirection.StartToEnd -> Color.Yellow
        DismissDirection.EndToStart -> Color.Green
        null -> Color.Transparent
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color)
    ) {
        if (direction == DismissDirection.StartToEnd) {
            Log.d("StartToEndOffset", dismissState.offset.value.toString())
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .fillMaxHeight()
                    .offset {
                        IntOffset(
                            -220.dp.roundToPx() + dismissState.offset.value.roundToInt(),
                            0
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Text(
                    "Picked",
                    color = Color.Black,
                    modifier = Modifier
                )
            }
        }
        if (direction == DismissDirection.EndToStart) {
            Log.d("EndToStartOffset", dismissState.offset.value.toString())
            Column(
                modifier = Modifier
                    .width(220.dp)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd)
                    .offset {
                        IntOffset(
                            220.dp.roundToPx() + dismissState.offset.value.roundToInt(),
                            0
                        )
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                Text(
                    text = "Unable To Pick",
                    color = Color.Black,
                    modifier = Modifier
                )
            }
        }

    }
}