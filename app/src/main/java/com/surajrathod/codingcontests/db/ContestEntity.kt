package com.surajrathod.codingcontests.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contest_table")
data class ContestEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val duration: String,
    val end_time: String,
    val in_24_hours: String,
    val name: String,
    val site: String,
    val start_time: String,
    val status: String,
    val url: String
)
