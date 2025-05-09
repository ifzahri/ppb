package ppb.iftala.ets.mymoneynotes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ppb.iftala.ets.mymoneynotes.data.model.Transaction

@Database(entities = [Transaction::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}