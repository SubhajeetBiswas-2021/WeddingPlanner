package com.subhajeet.weddingplanner.viewModel

import androidx.lifecycle.ViewModel
import com.subhajeet.weddingplanner.model.BudgetSplit
import com.subhajeet.weddingplanner.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class BudgetViewModel  @Inject constructor() : ViewModel() {

    // backing state for budget
    private val _budget = MutableStateFlow(0.0)
    val budget: StateFlow<Double> = _budget

    // split percentages (you can change these)
    private val venuePercent = 0.5   // 50% of budget
    private val cateringPercent = 0.3 // 30%
    private val decorPercent = 0.2    // 20%

    // derived state: budget split
    private val _split = MutableStateFlow(BudgetSplit(0.0, 0.0, 0.0))
    val split: StateFlow<BudgetSplit> = _split

    // update budget
    fun setBudget(amount: Double) {
        _budget.value = amount
        calculateSplit(amount)
    }

    private fun calculateSplit(amount: Double) {
        _split.update {
            BudgetSplit(
                venue = amount * venuePercent,
                catering = amount * cateringPercent,
                decor = amount * decorPercent
            )
        }
    }
}