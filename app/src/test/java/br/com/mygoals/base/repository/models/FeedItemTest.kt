package br.com.mygoals.base.repository.models

import br.com.mygoals.testUtil.FeedTestDataUtil
import junit.framework.TestCase.assertEquals
import org.junit.Test

class FeedItemTest {

    private val testDataUtil = FeedTestDataUtil()

    @Test
    fun getRule_shouldReturnRuleWithIdAndNoType() {
        val id = 1
        val feedItem = testDataUtil.feedItem(id)
        val expected = Rule(id, null)
        assertEquals(expected, feedItem?.getRule())
    }

}
