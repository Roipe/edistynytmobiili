package com.example.edistynytmobiili.view

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.edistynytmobiili.viewmodel.LogoutViewModel

@Composable
fun LogoutConfirmationDialog(onConfirm : () -> Unit, onCancel: () -> Unit, errorString: String?) {

        val context = LocalContext.current
        LaunchedEffect(key1 = errorString) {
            //letin avulla voidaan suorittaa koodiblock mikäli nullable-arvo on jotain muuta kuin null.
            errorString?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        AlertDialog(
            title = {Text("Confirm logout")},
            text = { Text("Are you sure you want to logout?") },
            onDismissRequest = { /*TODO*/ },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Logout")
                }
            }, dismissButton = {
                TextButton(onClick = { onCancel() }) {
                    Text("Cancel")
                }
            }
        )

}
@Composable
fun LogoutScreen(goToLogin : () -> Unit, cancelLogout : () -> Unit) {
    val vm: LogoutViewModel = viewModel()

    /*
    LaunchedEffect(key1 = vm.logoutState.value.logoutStatus) {
        if (vm.logoutState.value.logoutStatus) {
            goToLogin()
        }
    }
    */

    //vm.logout()
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            vm.logoutState.value.loading -> CircularProgressIndicator(modifier = Modifier.align(
                Alignment.Center))
            !vm.logoutState.value.logoutStatus -> LogoutConfirmationDialog(
                onConfirm = { vm.logout() },
                onCancel = { cancelLogout() },
                errorString = vm.logoutState.value.errorMsg

            )
            else -> Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                Text("Logged out successfully")
                Button(onClick = { goToLogin() }) {
                    Text("Back to login")
                }
            }
        }
    }
}