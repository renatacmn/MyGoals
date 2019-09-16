package br.com.mygoals.goals.list

import androidx.databinding.ObservableField
import br.com.mygoals.base.api.models.Goal

data class GoalsListViewState(
    var isLoading: ObservableField<Boolean> = ObservableField(false),
    var isSuccess: ObservableField<Boolean> = ObservableField(false),
    var isError: ObservableField<Boolean> = ObservableField(false),
    var goalsList: ObservableField<List<Goal>> = ObservableField(emptyList()),
    var errorMessage: ObservableField<String?> = ObservableField("")
)
