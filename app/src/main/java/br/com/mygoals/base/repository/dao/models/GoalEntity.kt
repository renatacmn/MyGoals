package br.com.mygoals.base.repository.models

import android.content.Context
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.mygoals.R
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Entity
@Parcelize
data class Goal(
    @PrimaryKey
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "goalImageURL")
    val goalImageURL: String,
    @field:Json(name = "currentBalance")
    val currentBalance: Float,
    @field:Json(name = "targetAmount")
    val targetAmount: Float?,

    var lastRefresh: Date?
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