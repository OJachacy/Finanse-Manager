package com.example.myapplication

import com.example.myapplication.model.Expense
import java.time.LocalDate

object Shared {
    val expenses = mutableListOf<Expense>().apply {
        add(
            Expense("Biedronka",
            -12.0F,
                LocalDate.now(),
                "Jedzenie"
        )
        )
        add(
            Expense("Lidl",
                -12.0F,
                LocalDate.now(),
                "Jedzenie"
            )
        )
        add(
            Expense("Sketch",
                -150.0F,
                LocalDate.now(),
                "Rozrywka"
            )
        )
        add(
            Expense("Apteka",
                -120.0F,
                LocalDate.now(),
                "Zdrowie"
            )
        )
        add(
            Expense("Sketch",
                -20.0F,
                LocalDate.now(),
                "Rozrywka"
            )
        )
        add(
            Expense("Apteka",
                -22.0F,
                LocalDate.now(),
                "Zdrowie"
            )
        )
        add(
            Expense("Hocki klocki",
                -52.0F,
                LocalDate.now(),
                "Rozrywka"
            )
        )
        add(
            Expense("Praca",
                5000.0F,
                LocalDate.now(),
                "Przychody"
            )
        )
    }
}