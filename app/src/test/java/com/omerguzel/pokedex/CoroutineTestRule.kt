package com.omerguzel.pokedex

import app.cash.turbine.TurbineContext
import app.cash.turbine.turbineScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class TestCoroutineRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
fun runTestAndCleanUp(
    coroutineDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    block: suspend TestScope.() -> Unit,
) = runTest(coroutineDispatcher) {
    try {
        block()
    } finally {
        val timeAfterTest = currentTime
        advanceUntilIdle()
        assertEquals(
            timeAfterTest,
            currentTime
        )
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
fun runTestAndCleanUpWithTurbineScope(
    coroutineDispatcher: CoroutineDispatcher = UnconfinedTestDispatcher(),
    block: suspend TurbineContext.(testScope: TestScope) -> Unit,
) = runTestAndCleanUp(coroutineDispatcher) {
    turbineScope {
        block(this@runTestAndCleanUp)
    }
}
