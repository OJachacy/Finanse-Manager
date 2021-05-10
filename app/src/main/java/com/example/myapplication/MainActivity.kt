package com.example.myapplication
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapter.ExpenseViewHolder
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Expense
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val expenseAdapter by lazy {
        ExpenseViewHolder.ExpensesAdapter(Shared.expenses, {
            openEditActivity(it)
        }, {
            deleteDialog(it)
        })
    }


    private fun deleteDialog(expense: Expense) {
        val deletePopup = AlertDialog.Builder(this)
        deletePopup.setMessage("Chcesz usunąć wpis?")
            .setPositiveButton("Tak") { dialog, id ->
                expenseAdapter.deleteExpense(expense)
                updateSummary()
            }
            .setNegativeButton("Nie") { dialog, position ->
                dialog.dismiss()
            }
        val alert = deletePopup.create()
        alert.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupExpensesList()
        binding.addButton.setOnClickListener { openAddActivity() }
        binding.sumButton.setOnClickListener { openChartActivity() }
    }

    private fun setupExpensesList() {
        binding.expensesList.apply {
            adapter = expenseAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun updateSummary() {
        var income = 0F
        var outgo = 0F
        var balance = 0F
        binding.balance.setText(balance.toString())
        binding.income.setText(income.toString())
        binding.outgo.setText(outgo.toString())
        for (expense in Shared.expenses) {
            if (expense.date.isAfter(LocalDate.now().minusMonths(1))) {
                balance+=expense.amount
                binding.balance.setText(balance.toString())
                when (expense.category) {
                    AddActivity.EnumCategory.INCOME.category -> {
                        income += expense.amount
                        binding.income.setText(income.toString())
                    }
                    else -> {
                        outgo += expense.amount
                        binding.outgo.setText(outgo.toString())
                    }
                }
            }
        }
        expenseAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        expenseAdapter.list = Shared.expenses
        updateSummary()
    }

    private fun openAddActivity() {
        startActivity(Intent(this, AddActivity::class.java))
    }

    private fun openChartActivity() {
        startActivity(Intent(this, ChartActivity::class.java))
    }

    private fun openEditActivity(expense: Expense) {
        val newIntent = Intent(this, AddActivity::class.java)
        newIntent.putExtra("Object", expense)
        startActivity(newIntent)
    }
}