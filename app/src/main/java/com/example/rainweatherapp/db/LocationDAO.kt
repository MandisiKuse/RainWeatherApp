package com.example.rainweatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.rainweatherapp.models.Location

@Dao
interface LocationDAO {
    @Query("SELECT * FROM location")
    fun getAllLocations(): List<Location>


    @Insert
    fun insertLocation(vararg user: Location)
}