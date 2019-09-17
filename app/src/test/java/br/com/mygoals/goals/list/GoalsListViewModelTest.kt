package br.com.mygoals.goals.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.mygoals.base.repository.GoalsRepository
import org.junit.Rule
import org.mockito.Mock

class GoalsListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: GoalsRepository

    private lateinit var viewModel: GoalsListViewModel

//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        this.viewModel = GoalsListViewModel(repository, Executors)
//    }
//
//    @Test
//    fun getSavingsGoals_onCall_shouldShowLoadingAndFetchData() {
//        viewModel.getSavingsGoals()
//        assertTrue(viewModel.viewState.isLoading.get() == true)
//        verify(repository, times(1)).getSavingsGoals(viewModel)
//    }

}
