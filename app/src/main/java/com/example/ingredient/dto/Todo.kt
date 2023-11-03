package com.example.ingredient.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "todoTable")
class Todo(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "state") var state: Boolean,
    @ColumnInfo(name = "spin") var spin : String,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "count") var count: String,
    @ColumnInfo(name = "place") var place: String,
    @ColumnInfo(name = "timestamp") var timestamp: Long,
    @ColumnInfo(name = "comment") var comment: String,
): Serializable {
}