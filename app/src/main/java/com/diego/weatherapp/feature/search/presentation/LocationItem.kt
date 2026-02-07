package com.diego.weatherapp.feature.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.diego.weatherapp.domain.model.Location

@Composable
fun LocationItem(location: Location) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
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
