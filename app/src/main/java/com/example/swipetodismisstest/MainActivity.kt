package com.example.swipetodismisstest

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.swipetodismisstest.ui.theme.SwipeToDismissTestTheme
import com.example.swipetodismisstest.ui.theme.composables.CameraPreviewScreen
import com.example.swipetodismisstest.ui.theme.composables.OrderProductItemsList
import com.example.swipetodismisstest.ui.theme.composables.ProductItemSwipeOptions
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeToDismissTestTheme {
                // A surface container using the 'background' color from the theme
                val productItems = viewModel.productItems.collectAsState()
                val scannedItem = viewModel.scannedItem.collectAsState(initial = null)
                val cameraPermission = rememberPermissionState(android.Manifest.permission.CAMERA)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        when {
                            cameraPermission.status.isGranted -> {
                                CameraPreviewScreen {
                                    viewModel.onBarcodeScanned(it)
                                }
                            }

                            else -> {
                                LaunchedEffect(Unit) {
                                    cameraPermission.launchPermissionRequest()
                                }
                                Column(Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
                                    Icon(
                                        Icons.Rounded.Close,
                                        contentDescription = null,
                                        tint = MaterialTheme.colors.onBackground
                                    )
                                    Spacer(Modifier.height(8.dp))
                                    Text(
                                        "Camera permission required",
                                        style = MaterialTheme.typography.h6
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text("This is required in order for the app to take pictures")
                                }
                            }
                        }
                        OrderProductItemsList(
                            productItems = productItems.value,
                            scannedItemId = scannedItem,
                            onItemPicked = {
                                viewModel.onItemPicked(it)
                            },
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}