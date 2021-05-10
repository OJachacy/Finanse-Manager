package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Shared
import com.example.myapplication.databinding.ItemExpenseBinding
import com.example.myapplication.model.Expense

class ExpenseViewHolder(private val binding: ItemExpenseBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindExpense(expense: Expense, shortListener: (Expense) -> Unit, longListener: (Expense) -> Unit) {
        with(binding) {
            place.text = expense.place
            amount.text = expense.amount.toString()
            date.text = expense.date.toString()
            category.text = expense.category
            constraintLayout.setOnClickListener { shortListener(expense) }
            constraintLayout.setOnLongClickListener {
                longListener(expense)
                true
            }
        }
    }

    class ExpensesAdapter(
        initList: List<Expense>,
        private val shortListener: (Expense) -> Unit,
        private val longListener: (Expense) -> Unit
    ) : RecyclerView.Adapter<ExpenseViewHolder>() {
        var list: List<Expense> = initList
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
            val binding = ItemExpenseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            return ExpenseViewHolder(binding)
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
            holder.bindExpense(list[position], shortListener, longListener)
        }

        fun deleteExpense(expense: Expense) {
            Shared.expenses.remove(expense)
            notifyDataSetChanged()
        }
    }


}