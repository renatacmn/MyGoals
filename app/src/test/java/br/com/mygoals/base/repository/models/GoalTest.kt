package br.com.mygoals.base.repository.models

import android.content.Context
import br.com.mygoals.R
import br.com.mygoals.testUtil.BaseUnitTest
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock

class GoalTest: BaseUnitTest() {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mock(Context::class.java)
    }

    @Test
    fun getValueText_ifTargetAmountIsNull_shouldReturnCorrectString() {
        val currentBalance = 0f
        val goal = Goal(
            1,
            "Goal 1",
            "",
            currentBalance,
            null
        )

        whenever(context.getString(anyInt(), any())).thenReturn("")

        goal.getValueText(context)
        verify(context).getString(
            R.string.list_item_goal_value_without_target,
            currentBalance
        )
    }

    @Test
    fun getValueText_ifTargetAmountIsNotNull_shouldReturnCorrectString() {
        val currentBalance = 0f
        val targetAmount = 0f
        val goal = Goal(
            1,
            "Goal 1",
            "",
            currentBalance,
            targetAmount
        )

        whenever(context.getString(anyInt(), any())).thenReturn("")

        goal.getValueText(context)
        verify(context).getString(
            R.string.list_item_goal_value_with_target,
            currentBalance,
            targetAmount
        )
    }

}
