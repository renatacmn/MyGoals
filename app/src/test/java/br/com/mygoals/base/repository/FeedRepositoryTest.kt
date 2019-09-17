package br.com.mygoals.base.repository

import br.com.mygoals.base.repository.api.MyGoalsApi
import br.com.mygoals.base.repository.dao.FeedItemDao
import br.com.mygoals.testUtil.BaseRepositoryTest
import br.com.mygoals.testUtil.FeedRepositoryTestDataUtil
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class FeedRepositoryTest : BaseRepositoryTest() {

    @Mock
    private lateinit var api: MyGoalsApi

    @Mock
    private lateinit var feedItemDao: FeedItemDao

    private lateinit var feedRepository: FeedRepository

    private val testDataUtil = FeedRepositoryTestDataUtil()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        feedRepository = FeedRepository(api, feedItemDao)
    }

    @Test
    fun getFeed_ifHasFeedItemsSaved_shouldLoadFromDatabase() {
        whenever(feedItemDao.hasFeedItems(anyInt(), any()))
            .thenReturn(Single.just(testDataUtil.feedItemEntity()))

        val result = feedRepository.getFeed(testDataUtil.goalId()).test()
        result.awaitTerminalEvent()

        verify(feedItemDao, times(1)).loadFeedItems(anyInt())
    }

    @Test
    fun getFeed_ifHasFeedItemsSaved_shouldNotCallApi() {
        whenever(feedItemDao.hasFeedItems(anyInt(), any()))
            .thenReturn(Single.just(testDataUtil.feedItemEntity()))

        feedRepository.getFeed(testDataUtil.goalId())
        verifyZeroInteractions(api)
    }

    @Test
    fun getFeed_ifDoesNotHaveFeedItemsSaved_shouldCallApi() {
        whenever(feedItemDao.hasFeedItems(anyInt(), any()))
            .thenReturn(Single.error(RuntimeException()))

        val result = feedRepository.getFeed(testDataUtil.goalId()).test()
        result.awaitTerminalEvent()

        verify(api, times(1)).getFeed(anyInt())
    }

    @Test
    fun getFeed_onApiSuccess_shouldSaveOnDatabase() {
        whenever(feedItemDao.hasFeedItems(anyInt(), any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getFeed(anyInt()))
            .thenReturn(Single.just(testDataUtil.feedApiModel()))

        val result = feedRepository.getFeed(testDataUtil.goalId()).test()
        result.awaitTerminalEvent()

        verify(feedItemDao, times(1)).saveFeedItems(anyInt(), any())
    }

    @Test
    fun getFeed_onApiSuccess_shouldLoadFromDatabase() {
        whenever(feedItemDao.hasFeedItems(anyInt(), any()))
            .thenReturn(Single.error(RuntimeException()))

        whenever(api.getFeed(anyInt()))
            .thenReturn(Single.just(testDataUtil.feedApiModel()))

        val result = feedRepository.getFeed(testDataUtil.goalId()).test()
        result.awaitTerminalEvent()

        verify(feedItemDao, times(1)).loadFeedItems(anyInt())
    }

}
