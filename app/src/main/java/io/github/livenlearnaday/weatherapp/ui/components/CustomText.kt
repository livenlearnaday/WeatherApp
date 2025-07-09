package io.github.livenlearnaday.weatherapp.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import io.github.livenlearnaday.weatherapp.ui.theme.Typography

@Composable
fun textWithMultiStyle(
    originalText: String,
    customTextList: List<TextWithStyle>
): AnnotatedString {
    var annotatedString = buildAnnotatedString { append(originalText) }

    customTextList.forEach { textExcerpt ->
        annotatedString = buildAnnotatedString {
            val startIndex = annotatedString.indexOf(textExcerpt.customText)
            val endIndex = startIndex + textExcerpt.customText.length
            append(annotatedString)
            addStyle(
                style = textExcerpt.style.toSpanStyle(),
                start = startIndex,
                end = endIndex
            )
        }
    }
    return annotatedString
}

data class TextWithStyle(
    val customText: String,
    val style: TextStyle
)

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Text(
        text = textWithMultiStyle(
            originalText = "weatherstack @ 2025-04-19",
            customTextList = listOf(
                TextWithStyle(
                    customText = "weatherstack",
                    style = Typography.labelLarge.copy(color = Color.Red)
                )
            )
        ),
        style = Typography.labelLarge
    )
}
