/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data

import android.app.Application
import com.squareup.sqldelight.db.SqlDriver
import okio.FileSystem
import okio.Path.Companion.toOkioPath
import tachiyomi.core.db.Transactions
import tachiyomi.core.prefs.AndroidPreferenceStore
import tachiyomi.data.catalog.AndroidCatalogInstallationChanges
import tachiyomi.data.catalog.AndroidCatalogInstaller
import tachiyomi.data.catalog.AndroidCatalogLoader
import tachiyomi.data.catalog.CatalogGithubApi
import tachiyomi.data.catalog.CatalogRemoteRepositoryImpl
import tachiyomi.data.category.CategoryRepositoryImpl
import tachiyomi.data.download.DownloadRepositoryImpl
import tachiyomi.data.history.HistoryRepositoryImpl
import tachiyomi.data.library.LibraryRepositoryImpl
import tachiyomi.data.library.LibraryUpdateSchedulerImpl
import tachiyomi.data.library.MangaCategoryRepositoryImpl
import tachiyomi.data.manga.ChapterRepositoryImpl
import tachiyomi.data.manga.MangaRepositoryImpl
import tachiyomi.data.updates.UpdatesRepositoryImpl
import tachiyomi.domain.catalog.service.CatalogInstallationChanges
import tachiyomi.domain.catalog.service.CatalogInstaller
import tachiyomi.domain.catalog.service.CatalogLoader
import tachiyomi.domain.catalog.service.CatalogPreferences
import tachiyomi.domain.catalog.service.CatalogRemoteApi
import tachiyomi.domain.catalog.service.CatalogRemoteRepository
import tachiyomi.domain.catalog.service.CatalogStore
import tachiyomi.domain.download.service.DownloadPreferences
import tachiyomi.domain.download.service.DownloadRepository
import tachiyomi.domain.history.service.HistoryRepository
import tachiyomi.domain.library.service.CategoryRepository
import tachiyomi.domain.library.service.LibraryCovers
import tachiyomi.domain.library.service.LibraryPreferences
import tachiyomi.domain.library.service.LibraryRepository
import tachiyomi.domain.library.service.LibraryUpdateScheduler
import tachiyomi.domain.library.service.MangaCategoryRepository
import tachiyomi.domain.manga.service.ChapterRepository
import tachiyomi.domain.manga.service.MangaRepository
import tachiyomi.domain.track.service.TrackPreferences
import tachiyomi.domain.ui.UiPreferences
import tachiyomi.domain.updates.service.UpdatesRepository
import toothpick.ktp.binding.bind
import toothpick.ktp.binding.module
import java.io.File

fun DataModule(context: Application) = module {

  val driver = DatabaseDriverFactory(context).create()
  val db = createDatabase(driver)
  val dbHandler = JvmDatabaseHandler(db, driver)

  bind<Database>().toInstance(db)
  bind<SqlDriver>().toInstance(driver)
  bind<DatabaseHandler>().toInstance(dbHandler)
  bind<Transactions>().toClass<DatabaseTransactions>()

  bind<FileSystem>().toInstance(FileSystem.SYSTEM)

  bind<MangaRepository>().toClass<MangaRepositoryImpl>().singleton()

  bind<ChapterRepository>().toClass<ChapterRepositoryImpl>().singleton()

  bind<CategoryRepository>().toClass<CategoryRepositoryImpl>().singleton()
  bind<MangaCategoryRepository>().toClass<MangaCategoryRepositoryImpl>().singleton()

  bind<LibraryRepository>().toClass<LibraryRepositoryImpl>().singleton()
  bind<LibraryPreferences>()
    .toProviderInstance { LibraryPreferences(AndroidPreferenceStore(context, "library")) }
    .providesSingleton()

  bind<LibraryCovers>()
    .toProviderInstance {
      LibraryCovers(
        FileSystem.Companion.SYSTEM,
        File(context.filesDir, "library_covers").toOkioPath()
      )
    }
    .providesSingleton()
  bind<LibraryUpdateScheduler>().toClass<LibraryUpdateSchedulerImpl>().singleton()

  bind<CatalogRemoteRepository>().toClass<CatalogRemoteRepositoryImpl>().singleton()
  bind<CatalogRemoteApi>().toClass<CatalogGithubApi>().singleton()
  bind<CatalogPreferences>()
    .toProviderInstance { CatalogPreferences(AndroidPreferenceStore(context, "catalog")) }
    .providesSingleton()

  bind<CatalogInstaller>().toClass<AndroidCatalogInstaller>().singleton()
  bind<CatalogStore>().singleton()
  bind<CatalogLoader>().toClass<AndroidCatalogLoader>().singleton()

  val catalogInstallationChanges = AndroidCatalogInstallationChanges(context)
  bind<CatalogInstallationChanges>().toInstance(catalogInstallationChanges)
  bind<AndroidCatalogInstallationChanges>().toInstance(catalogInstallationChanges)

  bind<DownloadRepository>().toClass<DownloadRepositoryImpl>().singleton()
  bind<DownloadPreferences>()
    .toProviderInstance {
      val defaultDownloads = context.getExternalFilesDir("downloads")!!
      DownloadPreferences(
        AndroidPreferenceStore(context, "download"), defaultDownloads.toOkioPath()
      )
    }
    .providesSingleton()

  bind<UiPreferences>()
    .toProviderInstance { UiPreferences(AndroidPreferenceStore(context, "ui")) }
    .providesSingleton()

  bind<TrackPreferences>()
    .toProviderInstance { TrackPreferences(AndroidPreferenceStore(context, "track")) }
    .providesSingleton()

  bind<UpdatesRepository>().toClass<UpdatesRepositoryImpl>().singleton()

  bind<HistoryRepository>().toClass<HistoryRepositoryImpl>().singleton()
}
