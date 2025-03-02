package com.example.simplecomposeproject.RateCoverter.offlinehistory
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RatesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRate(user: RatesModel)

    @Update
    suspend fun updateRate(user: RatesModel)

    @Delete
    suspend fun deleteRate(user: RatesModel)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllRates()

    @Query("SELECT * FROM user_table  ORDER BY id DESC")
    fun readAllData(): LiveData<List<RatesModel>>

    @Query("DELETE FROM user_table WHERE id NOT IN (SELECT id FROM user_table ORDER BY id DESC )")
    fun deleteExcessItems()

}