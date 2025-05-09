package ppb.iftala.ets.mymoneynotes.data.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AmountText(amount: Double, modifier: Modifier = Modifier) {
    val formattedAmount = NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(amount)
    Text(
        text = formattedAmount,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun DateText(date: Date, modifier: Modifier = Modifier) {
    val formattedDate = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale("id")).format(date)
    Text(
        text = formattedDate,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
fun SectionCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp, 12.dp, 16.dp, 4.dp)
        )
        content()
    }
}