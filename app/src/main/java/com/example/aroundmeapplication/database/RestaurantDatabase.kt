package com.example.aroundmeapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aroundmeapplication.dataclass.RestaurantData

@Database(entities = [RestaurantData::class], version = 1)
abstract class RestaurantDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    companion object{
        @Volatile
        private var instance:RestaurantDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance?: synchronized(LOCK){
            instance?:createDatabase(context).also{ instance = it}
        }

        private fun createDatabase(context: Context)=
            Room.databaseBuilder(
                context.applicationContext,RestaurantDatabase::class.java,
                "restro_db.db"
            ).build()
    }
}