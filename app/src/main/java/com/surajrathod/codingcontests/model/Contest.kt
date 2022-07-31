package com.surajrathod.codingcontests.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contest")
data class Contest(
    val duration: String,
    val end_time: String,
    val in_24_hours: String,
    val name: String,
    @PrimaryKey(autoGenerate = false)
    val site: String,
    val start_time: String,
    val status: String,
    val url: String
)