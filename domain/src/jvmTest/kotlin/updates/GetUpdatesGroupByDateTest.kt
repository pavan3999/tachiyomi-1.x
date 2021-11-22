/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.domain.updates

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDate
import kotlinx.datetime.toLocalDateTime
import tachiyomi.domain.updates.interactor.GetUpdatesGroupByDate
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository
import java.time.format.DateTimeFormatter

class GetUpdatesGroupByDateTest : StringSpec({
  val repository = mockk<UpdatesRepository>()
  val interactor = GetUpdatesGroupByDate(repository)
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
        1627906952000.formatAndParse(),
        1627734152100.formatAndParse()
      )
    }
  }
})

private fun mockkUpdates(mockkDateUploaded: Long): UpdatesManga {
  return mockk {
    every { dateUpload } returns mockkDateUploaded
    every { date } returns Instant.fromEpochMilliseconds(mockkDateUploaded).dateString
  }
}

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

private fun Long.formatAndParse(): LocalDate {
  val string = Instant.fromEpochMilliseconds(this).dateString
  return string.toLocalDate()
}

private val Instant.dateString
  get() = toLocalDateTime(TimeZone.UTC).toJavaLocalDateTime().format(formatter)
