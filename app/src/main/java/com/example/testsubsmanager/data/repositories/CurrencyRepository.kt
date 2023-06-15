package com.example.testsubsmanager.data.repositories
import androidx.lifecycle.LiveData
import com.example.testsubsmanager.dao.CurrencyDao
import com.example.testsubsmanager.data.models.Currency

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val allCurrencies: LiveData<List<Currency>> = currencyDao.getAllCurrencies()

    suspend fun insert(currency: Currency) {
        currencyDao.insert(currency)
    }

    suspend fun update(currency: Currency) {
        currencyDao.update(currency)
    }

    suspend fun delete(currency: Currency) {
        currencyDao.delete(currency)
    }

    fun getCurrencyByCode(code: String): LiveData<Currency> {
        return currencyDao.getCurrencyByCode(code)
    }
}
