package com.example.edistynytmobiili.view


import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.R
import com.example.edistynytmobiili.components.RandomImage
import com.example.edistynytmobiili.viewmodel.RentalSummaryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RentalSummaryScreen(goToCategories : () -> Unit, onMenuClick: () -> Unit) {
    val vm: RentalSummaryViewModel = viewModel()
    val context = LocalContext.current

    LaunchedEffect(key1 = vm.rentalSummaryState.value.errorMsg) {
        vm.rentalSummaryState.value.errorMsg?.let {
            Toast.makeText(context, vm.rentalSummaryState.value.errorMsg, Toast.LENGTH_LONG).show()
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(R.string.rental_summary)) },
            navigationIcon = { IconButton(onClick = { onMenuClick() }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = stringResource(R.string.menu))
            } }, actions = {IconButton(onClick = { goToCategories() }) {
                Icon(imageVector = Icons.Default.Check, contentDescription = stringResource(R.string.ok))
            } }
        )}
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.TopCenter
        ) {
            Column {
                Row ( modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center) {
                    Text(stringResource(R.string.rental_confirmed))
                }
                Spacer(Modifier.height(30.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly ){
                    RandomImage()
                    Column {
                        Text(stringResource(R.string.item))
                        Text(vm.rentalSummaryState.value.itemName, fontWeight = FontWeight.Medium)
                        Text(stringResource(R.string.rental_expires))
                        Text(stringResource(R.string.dd_mm_yyyy), fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(Modifier.height(30.dp))
                Row (modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = { goToCategories() }) {
                        Text(stringResource(R.string.back_to_main_menu))
                    }
                }

            }

        }
    }
}