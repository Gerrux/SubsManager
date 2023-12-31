package com.example.testsubsmanager.database.dao
import androidx.room.*
import com.example.testsubsmanager.database.entity.CurrencyDatabaseModel

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies")
    fun getAllCurrencies(): List<CurrencyDatabaseModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: CurrencyDatabaseModel)

    @Update
    suspend fun update(currency: CurrencyDatabaseModel)

    @Delete
    suspend fun delete(currency: CurrencyDatabaseModel)

    @Query("SELECT * FROM currencies WHERE code = :code")
    fun getCurrencyByCode(code: String): CurrencyDatabaseModel

    @Query("SELECT * FROM currencies WHERE code IN (:codes)")
    fun getCurrenciesByCodes(codes: List<String>): List<CurrencyDatabaseModel>
}
