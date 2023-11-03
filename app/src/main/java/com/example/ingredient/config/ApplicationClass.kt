package com.example.ingredient.config

import android.app.Application
import com.example.ingredient.repository.TodoRepository

class ApplicationClass: Application() {

    override fun onCreate() {
        super.onCreate()

        TodoRepository.initialize(this)

    }
}