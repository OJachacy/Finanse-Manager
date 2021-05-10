package com.example.myapplication

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityAddBinding
import com.example.myapplication.model.Expense
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AddActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val passed = intent.getSerializableExtra("Object") as? Expense
        setContentView(binding.root)
        setupSave(passed)
        passed?.let {
            binding.place.setText(passed.place)
            binding.amount.append(passed.amount.toString())
            binding.date.setText(passed.date.toString())
            binding.category.findViewById<RadioButton>(findCategoryIdByText(passed)).isChecked =
                true
        }
        val cal = Calendar.getInstance()

        binding.date.setOnClickListener {
            DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd"
                val sdf = SimpleDateFormat(myFormat)
                binding.date.text = sdf.format(cal.time)
            },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()

        }
    }

    private fun setupSave(passed: Expense?) {
        binding.saveButton.setOnClickListener {
        val categoryToText = binding.category.findViewById<RadioButton>(binding.category.checkedRadioButtonId).text.toString();

            val expense = Expense(
                binding.place.text.toString(),
                checkCategory(categoryToText),
                LocalDate.parse(binding.date.text.toString()),
                categoryToText
            )
            passed?.let { Shared.expenses.set(Shared.expenses.indexOf(passed),expense) }
                ?: Shared.expenses.add(expense)
            finish()
        }
    }

    private fun findCategoryIdByText(expense: Expense) : Int =
        when(expense.category){
            EnumCategory.FOOD.category -> R.id.radio_food
            EnumCategory.FUN.category -> R.id.radio_fun
            EnumCategory.HEALTH.category -> R.id.radio_health
            else -> R.id.radio_payments
        }



    private fun checkCategory(category: String): Float {
        if(category!="Przychody"){
            return binding.amount.text.toString().toFloat() * -1
        }else{
            return binding.amount.text.toString().toFloat()
        }
    }

    enum class EnumCategory(val category: String) {
        FOOD("Jedzenie"),
        FUN("Rozrywka"),
        INCOME("Przychody"),
        HEALTH("Zdrowie"),
    }
}