package com.example.simplecomposeproject.RateCoverter.offlinehistory

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class RatesModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,  // Let Room auto-generate the ID
    var from: String,
    var to: String,
    var rate: String,
    var time: String
)
