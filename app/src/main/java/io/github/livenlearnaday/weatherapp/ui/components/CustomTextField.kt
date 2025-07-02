package io.github.livenlearnaday.weatherapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    state: TextFieldState,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    textColor: Color = MaterialTheme.colorScheme.onBackground
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        state = state,
        textStyle = LocalTextStyle.current.copy(
            color = textColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        lineLimits = TextFieldLineLimits.SingleLine,
        cursorBrush = SolidColor(textColor),
        modifier = modifier
            .testTag("name")
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(
                width = 1.dp,
                color = if (isFocused) {
                    Color.Gray
                } else {
                    Color.Transparent
                },
                shape = RoundedCornerShape(8.dp)
            )
            .padding(12.dp)
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            },
        decorator = { innerBox ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.width(4.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 10.dp)
                ) {
                    if (state.text.isBlank()) {
                        Text(
                            text = hint,
                            color = Color.Black.copy(alpha = 0.6f),
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                    innerBox()
                }

                if (isFocused) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                onClick = {
                                    state.clearText()
                                }
                            ),
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        contentDescription = "Clear Text",
                        tint = textColor
                    )
                }
            }
        }
    )
}

fun Modifier.positionAwareImePadding() = composed {
    var consumePadding by remember { mutableIntStateOf(0) }
    this@positionAwareImePadding
        .onGloballyPositioned { coordinates ->
            val rootCoordinate = coordinates.findRootCoordinates()
            val bottom = coordinates.positionInWindow().y + coordinates.size.height

            consumePadding = (rootCoordinate.size.height - bottom).toInt().coerceAtLeast(0)
        }
        .consumeWindowInsets(PaddingValues(bottom = with(LocalDensity.current) { consumePadding.toDp() }))
        .imePadding()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
fun PreviewTextField() {
    WeatherAppTheme {
        CustomTextField(
            state = rememberTextFieldState(),
            hint = "Bangkok",
            keyboardType = KeyboardType.Text,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
