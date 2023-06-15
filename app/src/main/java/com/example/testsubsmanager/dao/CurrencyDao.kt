package com.example.testsubsmanager.dao
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.testsubsmanager.data.models.Currency

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies ORDER BY name ASC")
    fun getAllCurrencies(): LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(currency: Currency)

    @Update
    suspend fun update(currency: Currency)

    @Delete
    suspend fun delete(currency: Currency)

    @Query("SELECT * FROM currencies WHERE code = :code")
    fun getCurrencyByCode(code: String): LiveData<Currency>
}
