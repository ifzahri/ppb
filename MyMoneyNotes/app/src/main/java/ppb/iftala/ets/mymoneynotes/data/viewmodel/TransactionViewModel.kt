package ppb.iftala.ets.mymoneynotes.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ppb.iftala.ets.mymoneynotes.data.model.Transaction
import ppb.iftala.ets.mymoneynotes.data.model.TransactionType
import ppb.iftala.ets.mymoneynotes.data.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: TransactionRepository
) : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _totalIncome = MutableStateFlow(0.0)
    val totalIncome: StateFlow<Double> = _totalIncome.asStateFlow()

    private val _totalExpense = MutableStateFlow(0.0)
    val totalExpense: StateFlow<Double> = _totalExpense.asStateFlow()

    private val _balance = MutableStateFlow(0.0)
    val balance: StateFlow<Double> = _balance.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAllTransactions().collect { transactions ->
                _transactions.value = transactions
            }
        }

        viewModelScope.launch {
            repository.getTotalByType(TransactionType.INCOME).collect { income ->
                _totalIncome.value = income ?: 0.0
                calculateBalance()
            }
        }

        viewModelScope.launch {
            repository.getTotalByType(TransactionType.EXPENSE).collect { expense ->
                _totalExpense.value = expense ?: 0.0
                calculateBalance()
            }
        }
    }

    private fun calculateBalance() {
        _balance.value = _totalIncome.value - _totalExpense.value
    }

    fun addTransaction(
        type: TransactionType,
        category: String,
        amount: Double,
        date: Date = Date(),
        note: String = ""
    ) {
        val transaction = Transaction(
            type = type,
            category = category,
            amount = amount,
            date = date,
            note = note
        )

        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    fun getCategoryList(type: TransactionType): List<String> {
        return when (type) {
            TransactionType.INCOME -> listOf(
                "Gaji", "Bonus", "Hadiah", "Investasi", "Penjualan", "Lainnya"
            )
            TransactionType.EXPENSE -> listOf(
                "Makanan", "Transport", "Belanja", "Hiburan", "Tagihan", "Pendidikan",
                "Kesehatan", "Lainnya"
            )
        }
    }
}