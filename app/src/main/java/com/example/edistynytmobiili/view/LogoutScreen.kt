package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Login

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.R
import com.example.edistynytmobiili.viewmodel.LogoutViewModel

@Composable
fun LogoutConfirmationDialog(onConfirm : () -> Unit, onCancel: () -> Unit) {

    AlertDialog(
        title = {Text(stringResource(R.string.confirm_logout))},
        text = { Text(stringResource(R.string.are_you_sure_you_want_to_logout)) },
        onDismissRequest = { /*TODO*/ },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text(stringResource(R.string.log_out))
            }
        }, dismissButton = {
            TextButton(onClick = { onCancel() }) {
                Text(stringResource(R.string.cancel))
            }
        }
    )

}
@Composable
fun LogoutScreen(goToLogin : () -> Unit) {
    val vm: LogoutViewModel = viewModel()
    val context = LocalContext.current
    LaunchedEffect(key1 = vm.logoutState.value.errorMsg) {
        vm.logoutState.value.errorMsg?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            vm.logoutState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(
                Alignment.Center))
            !vm.logoutState.value.status -> vm.logout()
            else -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.logged_out_successfully), fontSize = 24.sp)
                Text("\uD83D\uDC4D",fontSize = 64.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Button(onClick = { goToLogin()}) {
                    Icon(Icons.Filled.Login, contentDescription = stringResource(R.string.back_to_login),
                        modifier = Modifier.padding(end = 10.dp))
                    Text(stringResource(R.string.log_back_in))
                }
            }
        }
    }
}