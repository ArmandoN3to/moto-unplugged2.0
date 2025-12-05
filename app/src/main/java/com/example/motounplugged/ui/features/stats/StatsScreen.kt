package com.example.motounplugged.ui.features.stats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.motounplugged.ui.components.StatCard
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

/**
 * A tela principal de Estatísticas.
 */
@Composable
fun StatsScreen(
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Column (
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(
                    text = "Estatísticas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Monitore seu desempenho de foco",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 5.dp)
                )
            }
            Spacer(Modifier.height(24.dp))
        }

        items(mockStatsList) { statItem ->
            StatCard(
                item = statItem,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp)
            )
        }
    }
}


data class StatItem(
    val icon: ImageVector,
    val label: String,
    val value: String,
    val subtitle: String
)

val mockStatsList = listOf(
    StatItem(
        icon = Icons.Default.LocalFireDepartment,
        label = "Sequência atual",
        value = "7",
        subtitle = "dias consecutivos"
    ),
    StatItem(
        icon = Icons.Default.TrendingUp,
        label = "Melhor sequência",
        value = "12",
        subtitle = "dias"
    ),
    StatItem(
        icon = Icons.Default.Schedule,
        label = "Tempo total recuperado",
        value = "45h 23min",
        subtitle = "este mês"
    ),
    StatItem(
        icon = Icons.Default.CheckCircleOutline,
        label = "Sessões completadas",
        value = "23",
        subtitle = "total"
    ),
    StatItem(
        icon = Icons.Default.Shield,
        label = "Apps bloqueados",
        value = "156",
        subtitle = "tentativas"
    ),
    StatItem(
        icon = Icons.Default.CalendarToday,
        label = "Média diária",
        value = "3h 15min",
        subtitle = "tempo de foco"
    )
)

@Preview(showBackground = true)
@Composable
fun StatisticsScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            StatsScreen()
        }
    }
}