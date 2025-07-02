package io.github.livenlearnaday.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.livenlearnaday.weatherapp.R
import io.github.livenlearnaday.weatherapp.ui.theme.royalBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    showBackArrow: Boolean = false,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors().copy(containerColor = royalBlue),
        navigationIcon = {
            if (showBackArrow) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_back),
                    contentDescription = "Go Back",
                    tint = Color.White,
                    modifier = Modifier //padding at the end of a component acts like margin
                        .padding(end = 20.dp)
                        .clickable {
                            onBackPressed()
                        }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewCustomTopAppBar() {
    MaterialTheme {
        CustomTopAppBar(
            title = "CountryList",
            onBackPressed = {}
        )
    }
}
