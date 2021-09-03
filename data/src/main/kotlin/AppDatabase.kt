/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.data

import android.content.Context
import android.os.Build
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import io.requery.android.database.sqlite.RequerySQLiteOpenHelperFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import tachiyomi.data.catalog.db.CatalogRemoteDao
import tachiyomi.data.download.db.DownloadDao
import tachiyomi.data.download.model.Download
import tachiyomi.data.history.db.HistoryDao
import tachiyomi.data.library.db.CategoryDao
import tachiyomi.data.library.db.LibraryDao
import tachiyomi.data.library.db.MangaCategoryDao
import tachiyomi.data.manga.db.ChapterDao
import tachiyomi.data.manga.db.MangaDao
import tachiyomi.data.updates.db.UpdatesDao
import tachiyomi.domain.catalog.model.CatalogRemote
import tachiyomi.domain.history.model.History
import tachiyomi.domain.library.model.Category
import tachiyomi.domain.library.model.LibraryManga
import tachiyomi.domain.library.model.MangaCategory
import tachiyomi.domain.manga.model.Chapter
import tachiyomi.domain.manga.model.Manga
import tachiyomi.domain.updates.model.UpdatesManga

@Database(
  entities = [Manga::class, Chapter::class, Category::class, MangaCategory::class,
    CatalogRemote::class, Download::class, History::class],
  views = [LibraryManga::class, UpdatesManga::class],
  version = 2
)
abstract class AppDatabase : RoomDatabase() {

  abstract val manga: MangaDao

  abstract val chapter: ChapterDao

  abstract val library: LibraryDao

  abstract val category: CategoryDao

  abstract val mangaCategory: MangaCategoryDao

  abstract val catalogRemote: CatalogRemoteDao

  abstract val download: DownloadDao

  abstract val updates: UpdatesDao

  abstract val history: HistoryDao

  companion object {
    fun build(context: Context): AppDatabase {
      return Room.databaseBuilder(context, AppDatabase::class.java, "tachiyomi")
        .openHelperFactory(
          // Support database inspector
          if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FrameworkSQLiteOpenHelperFactory()
          } else {
            RequerySQLiteOpenHelperFactory()
          }
        )
        .addCallback(object : RoomDatabase.Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            db.execSQL("""INSERT INTO category VALUES (${Category.ALL_ID}, "", 0, 0)""")
            db.execSQL("""INSERT INTO category VALUES (${Category.UNCATEGORIZED_ID}, "", 0, 0)""")
            db.execSQL("""CREATE TRIGGER system_categories_deletion_trigger
              BEFORE DELETE
              ON category
              BEGIN
              SELECT CASE
              WHEN OLD.id <= 0
              THEN RAISE(ABORT, 'System category cant be deleted')
              END;
              END;""")
            db.execSQL("CREATE INDEX favorite_manga_idx ON manga(favorite) WHERE favorite = 1")
            db.execSQL("""CREATE INDEX manga_chapters_unread_index ON chapter(mangaId, read) 
              WHERE read = 0""")
          }
        })
        // TODO test transactions with IO dispatchers
        .setQueryExecutor(Dispatchers.IO.asExecutor())
        .build()
    }
  }

}
