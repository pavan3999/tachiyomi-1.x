package tachiyomi.domain.updates

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import tachiyomi.domain.updates.interactor.GetUpdatesGroupByDate
import tachiyomi.domain.updates.model.UpdatesManga
import tachiyomi.domain.updates.service.UpdatesRepository
import java.text.SimpleDateFormat
import java.util.Date

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
      mockkSortedMap(
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

private fun mockkSortedMap(vararg mockkUpdates: UpdatesManga): Map<Date, List<UpdatesManga>> {
  return mockkUpdates.groupBy { formatter.parse(it.date) }
}

private fun mockkUpdates(mockkDateUploaded: Long): UpdatesManga {
  return mockk {
    every { dateUpload } returns mockkDateUploaded
    every { date } returns formatter.format(Date(dateUpload))
  }
}

private fun SimpleDateFormat.formatAndParse(epoch: Long): Date {
  val string = this.format(Date(epoch))
  return this.parse(string)
}