/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.history

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import tachiyomi.domain.history.interactor.GetHistoryByDate
import tachiyomi.domain.history.model.HistoryWithRelations
import tachiyomi.domain.history.service.HistoryRepository
import java.text.SimpleDateFormat
import java.util.Date

class GetHistoryByDateTest : StringSpec({
  val repository = mockk<HistoryRepository>()
  val interactor = GetHistoryByDate(repository)
  afterTest { clearAllMocks() }

  "called function" {
    every { repository.subscribeAll() } returns flowOf()
    interactor.subscribeAll()
    verify { repository.subscribeAll() }
  }
  "sorts by date" {
    every { repository.subscribeAll() } returns flowOf(
      listOf(
        mockkUpdates(1627906952000),
        mockkUpdates(1627906952000),
        mockkUpdates(1627734152000),
        mockkUpdates(1627734152100),
        mockkUpdates(1627734152110)
      )
    )

    interactor.subscribeAll().collect { updates ->
      updates.keys shouldContainExactlyInAnyOrder setOf(
        formatter.formatAndParse(1627906952000),
        formatter.formatAndParse(1627734152100)
      )
    }
  }
})

private val formatter = SimpleDateFormat("yy-MM-dd")

private fun mockkUpdates(mockkDateUploaded: Long): HistoryWithRelations {
  return mockk {
    every { readAt } returns mockkDateUploaded
    every { date } returns formatter.format(Date(readAt))
  }
}

private fun SimpleDateFormat.formatAndParse(epoch: Long): Date {
  val string = this.format(Date(epoch))
  return this.parse(string)
}
