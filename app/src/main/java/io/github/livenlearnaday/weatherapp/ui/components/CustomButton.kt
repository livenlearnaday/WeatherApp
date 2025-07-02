package io.github.livenlearnaday.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String = "",
    enableButton: Boolean = true,
    buttonHeight: Dp = 50.dp
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .requiredHeightIn(buttonHeight)
            .clickable(
                enabled = enableButton,
                interactionSource = null,
                indication = null,
                onClick = {
                    onClick()
                }
            ),
        enabled = enableButton,
        shape = RoundedCornerShape(8.dp),
        onClick = {
            onClick()
        },
        content = {
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewActionButton() {
    WeatherAppTheme {
        Column {
            ActionButton(
                label = stringResource(R.string.ok),
                onClick = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewActionButtonIsLoading() {
    WeatherAppTheme {
        Column {
            ActionButton(
                label = stringResource(R.string.ok),
                onClick = {},
                enableButton = false
            )
        }
    }
}
