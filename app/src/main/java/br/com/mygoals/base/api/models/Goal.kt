package br.com.mygoals.base.api.models

import android.content.Context
import br.com.mygoals.R
import com.squareup.moshi.Json

data class Goal(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "goalImageURL")
    val goalImageURL: String,
    @field:Json(name = "currentBalance")
    val currentBalance: Float,
    @field:Json(name = "targetAmount")
    val targetAmount: Float?
) {

    fun getValueText(context: Context): String {
        return if (targetAmount == null) {
            context.getString(
                R.string.list_item_goal_value_without_target,
                currentBalance
            )
        } else {
            context.getString(
                R.string.list_item_goal_value_with_target,
                currentBalance,
                targetAmount
            )
        }
    }

}