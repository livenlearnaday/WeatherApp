package io.github.livenlearnaday.weatherapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.livenlearnaday.weatherapp.ui.theme.WeatherAppTheme

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    imageResource: Int? = null,
    label: String,
    textColor: Color = Color.White,
    fontSize: TextUnit = 14.sp,
    showLoading: Boolean = false,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    shape: Shape = RoundedCornerShape(10.dp),
    enableButton: Boolean = true,
    onButtonClicked: () -> Unit
) {
    Button(
        enabled = enableButton,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            onButtonClicked()
        },
        shape = shape,
        colors = colors,
        content = {
            if (showLoading) {
                DotsPulsing()
            } else {
                if (imageResource != null) {
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = ""
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(6.dp),
                    text = label,
                    style = LocalTextStyle.current.copy(
                        color = textColor,
                        fontSize = fontSize,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewActionButton() {
    WeatherAppTheme {
        Column {
            CustomButton(
                modifier = Modifier
                    .wrapContentWidth(),
                label = "Search",
                showLoading = false,
                onButtonClicked = {}
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewActionButtonIsLoading() {
    WeatherAppTheme {
        Column {
            CustomButton(
                modifier = Modifier
                    .wrapContentWidth(),
                label = "Search",
                showLoading = true,
                onButtonClicked = {}
            )
        }
    }
}
