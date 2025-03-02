package com.example.simplecomposeproject.RateCoverter.offlinehistory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RatesModel::class], version = 1, exportSchema = false)
abstract class RatesDatabase : RoomDatabase() {
    abstract fun rateDao(): RatesDao
}
