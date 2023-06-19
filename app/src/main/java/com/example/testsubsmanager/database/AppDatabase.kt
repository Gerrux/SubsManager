package com.example.testsubsmanager.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testsubsmanager.database.dao.CurrencyDao
import com.example.testsubsmanager.database.dao.NotificationDao
import com.example.testsubsmanager.database.dao.SubscriptionDao
import com.example.testsubsmanager.database.entity.CurrencyDatabaseModel
import com.example.testsubsmanager.database.entity.NotificationDatabaseModel
import com.example.testsubsmanager.database.entity.SubscriptionDatabaseModel
import com.example.testsubsmanager.database.utils.LocalDateConverter

@Database(entities = [SubscriptionDatabaseModel::class, NotificationDatabaseModel::class, CurrencyDatabaseModel::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun notificationDao(): NotificationDao
    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
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
