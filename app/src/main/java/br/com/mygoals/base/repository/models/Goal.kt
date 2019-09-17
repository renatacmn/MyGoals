package br.com.mygoals.base.repository.models

import android.content.Context
import android.os.Parcelable
import br.com.mygoals.R
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Goal(
    val id: Int,
    val name: String,
    val goalImageURL: String,
    val currentBalance: Float,
    val targetAmount: Float?
) : Parcelable {

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
