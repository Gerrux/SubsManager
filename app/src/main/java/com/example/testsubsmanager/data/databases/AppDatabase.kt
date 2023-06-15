package com.example.testsubsmanager.data.databases
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testsubsmanager.dao.CurrencyDao
import com.example.testsubsmanager.dao.NotificationDao
import com.example.testsubsmanager.dao.SubscriptionDao

@Database(entities = [SubscriptionDatabaseModel::class, NotificationDatabaseModel::class, CurrencyDatabaseModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
