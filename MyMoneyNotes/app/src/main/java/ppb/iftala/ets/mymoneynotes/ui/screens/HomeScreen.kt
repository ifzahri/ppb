package ppb.iftala.ets.mymoneynotes.data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ppb.iftala.ets.mymoneynotes.ui.components.AmountText
import ppb.iftala.ets.mymoneynotes.ui.components.SectionCard
import ppb.iftala.ets.mymoneynotes.ui.viewmodel.TransactionViewModel

@Composable
fun HomeScreen(
    navigateToAddTransaction: () -> Unit,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val totalIncome by viewModel.totalIncome.collectAsState()
    val totalExpense by viewModel.totalExpense.collectAsState()
    val balance by viewModel.balance.collectAsState()
    val transactions by viewModel.transactions.collectAsState()

    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToAddTransaction) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Summary Card
            SectionCard(title = "Ringkasan") {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Saldo:", style = MaterialTheme.typography.bodyLarge)
                        AmountText(
                            amount = balance,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Pemasukan:", style = MaterialTheme.typography.bodyMedium)
                        AmountText(
                            amount = totalIncome,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Pengeluaran:", style = MaterialTheme.typography.bodyMedium)
                        AmountText(
                            amount = totalExpense,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            // Tabs
            TabRow(selectedTabIndex = selectedTabIndex) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Semua") }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Chart") }
                )
            }

            when (selectedTabIndex) {
                0 -> TransactionListScreen(
                    transactions = transactions,
                    onDeleteTransaction = { viewModel.deleteTransaction(it) }
                )
                1 -> TransactionChartScreen(
                    totalIncome = totalIncome,
                    totalExpense = totalExpense
                )
            }
        }
    }
}