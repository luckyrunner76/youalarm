package com.example.ingredient.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ingredient.dto.Todo
import com.example.ingredient.dao.TodoDao

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}