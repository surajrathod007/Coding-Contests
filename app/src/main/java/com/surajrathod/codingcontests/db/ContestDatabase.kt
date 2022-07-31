package com.surajrathod.codingcontests.db

import android.content.Context
import androidx.room.Database
import androidx.room.Index
import androidx.room.Room
import androidx.room.RoomDatabase
import com.surajrathod.codingcontests.model.Contest


@Database(entities = [ContestEntity::class], version = 1)
abstract class ContestDatabase : RoomDatabase() {

    abstract fun contestDao(): ContestDao

    companion object {

        @Volatile
        private var Instance: ContestDatabase? = null

        fun getDatabase(context: Context): ContestDatabase {

            if (Instance == null) {
                synchronized(this) {
                    Instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContestDatabase::class.java,
                        "contest_db"
                    ).build()
                }
            }

            return Instance!!
        }


    }
}