package ppb.iftala.ets.mymoneynotes.data.repository

import ppb.iftala.ets.mymoneynotes.data.db.TransactionDao
import ppb.iftala.ets.mymoneynotes.data.model.Transaction
import ppb.iftala.ets.mymoneynotes.data.model.TransactionType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getAllTransactions(): Flow<List<Transaction>> {
        return transactionDao.getAllTransactions()
    }

    fun getTransactionsByType(type: TransactionType): Flow<List<Transaction>> {
        return transactionDao.getTransactionsByType(type)
    }

    fun getTotalByType(type: TransactionType): Flow<Double?> {
        return transactionDao.getTotalByType(type)
    }

    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction)
    }
}