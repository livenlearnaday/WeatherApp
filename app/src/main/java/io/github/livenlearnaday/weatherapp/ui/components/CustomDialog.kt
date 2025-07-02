package io.github.livenlearnaday.weatherapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R

@Composable
fun CommonAlertDialog(
    onClose: () -> Unit,
    onConfirm: () -> Unit,
    dialogText: String
) {
    AlertDialog(
        text = {
            Text(text = dialogText)
        },
        shape = RoundedCornerShape(8.dp),
        onDismissRequest = onClose,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text(stringResource(R.string.ok))
            }
        }
    )
}
