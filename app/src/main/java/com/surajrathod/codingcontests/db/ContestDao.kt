package com.surajrathod.codingcontests.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.surajrathod.codingcontests.model.Contest


@Dao
interface ContestDao {

    @Insert
    suspend fun insert(contest : ContestEntity)

    @Delete
    suspend fun delete(contest: ContestEntity)

    @Query("select exists(select * from contest_table where name = :name and start_time= :startdate)")
    suspend fun isExists(name : String,startdate : String) : Boolean

    @Query("delete from contest_table where name = :name and start_time= :startdate")
    suspend fun remove(name : String,startdate : String)

    @Query("select * from contest_table")
    fun getAllContest() : LiveData<List<ContestEntity>>

    @Query("delete from contest_table where name = :name")
    suspend fun removeAlarm(name : String)

}