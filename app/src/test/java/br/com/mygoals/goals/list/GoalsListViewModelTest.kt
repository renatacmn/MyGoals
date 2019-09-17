package br.com.mygoals.goals.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mygoals.base.repository.GoalsRepository
import br.com.mygoals.testUtil.GoalsTestDataUtil
import br.com.mygoals.testUtil.TestExecutors
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GoalsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: GoalsRepository

    private lateinit var viewModel: GoalsListViewModel

    private val testDataUtil = GoalsTestDataUtil()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        this.viewModel = GoalsListViewModel(repository, TestExecutors())
    }

    @Test
    fun getSavingsGoals_onCall_shouldShowLoadingAndFetchData() {
        whenever(repository.getSavingsGoals())
            .thenReturn(Single.just(testDataUtil.savingsGoals()))

        viewModel.getSavingsGoals()
        assertTrue(viewModel.viewState.isLoading.get() == true)
        verify(repository, times(1)).getSavingsGoals()
    }

}
