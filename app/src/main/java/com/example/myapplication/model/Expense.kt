package com.example.myapplication.model

import java.io.Serializable
import java.time.LocalDate

data class Expense(
    val place: String,
    val amount: Float,
    val date: LocalDate,
    val category: String
):Serializable