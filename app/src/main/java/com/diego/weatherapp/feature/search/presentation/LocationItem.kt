package com.diego.weatherapp.feature.search.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diego.weatherapp.domain.model.Location

@Composable
fun LocationItem(
    location: Location,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = location.country,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }

    HorizontalDivider()
}
