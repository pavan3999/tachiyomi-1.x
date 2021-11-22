/*
 * Copyright (C) 2018 The Tachiyomi Open Source Project
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package tachiyomi.ui.browse.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.GetApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tachiyomi.domain.catalog.model.Catalog
import tachiyomi.domain.catalog.model.CatalogBundled
import tachiyomi.domain.catalog.model.CatalogInstalled
import tachiyomi.domain.catalog.model.CatalogLocal
import tachiyomi.domain.catalog.model.CatalogRemote
import tachiyomi.domain.catalog.model.InstallStep
import tachiyomi.i18n.MR
import tachiyomi.i18n.localize
import tachiyomi.ui.browse.Language
import tachiyomi.ui.core.components.DropdownMenu
import tachiyomi.ui.core.components.DropdownMenuItem
import tachiyomi.ui.core.components.EmojiText
import tachiyomi.ui.core.components.LetterIcon
import tachiyomi.ui.core.image.rememberImagePainter
import kotlin.math.max

@Composable
fun CatalogItem(
  catalog: Catalog,
  installStep: InstallStep? = null,
  onClick: (() -> Unit)? = null,
  onInstall: (() -> Unit)? = null,
  onUninstall: (() -> Unit)? = null,
  onPinToggle: (() -> Unit)? = null
) {
  val title = buildAnnotatedString {
    append("${catalog.name} ")
    val versionSpan = SpanStyle(
      fontSize = 12.sp,
      color = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
    )
    if (catalog is CatalogInstalled) {
      withStyle(versionSpan) { append("v${catalog.versionCode}") }
    } else if (catalog is CatalogRemote) {
      withStyle(versionSpan) { append("v${catalog.versionCode}") }
    }
  }
  val lang = when (catalog) {
    is CatalogBundled -> null
    is CatalogInstalled -> catalog.source.lang
    is CatalogRemote -> catalog.lang
  }?.let { Language(it).toEmoji() }

  Layout(
    modifier = onClick?.let { Modifier.clickable(onClick = it) } ?: Modifier,
    content = {
      CatalogPic(
        catalog = catalog,
        modifier = Modifier
          .layoutId("pic")
          .padding(12.dp)
          .size(48.dp)
      )
      if (lang != null) {
        EmojiText(
          text = lang,
          modifier = Modifier
            .layoutId("lang")
            .padding(8.dp)
        )
      }
      Text(
        text = title,
        style = MaterialTheme.typography.subtitle1,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .layoutId("title")
          .padding(top = 12.dp)
      )
      Text(
        text = catalog.description,
        style = MaterialTheme.typography.body2,
        color = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier
          .layoutId("desc")
          .padding(bottom = 12.dp, end = 12.dp)
      )
      CatalogButtons(
        catalog = catalog,
        installStep = installStep,
        onInstall = onInstall,
        onUninstall = onUninstall,
        onPinToggle = onPinToggle,
        modifier = Modifier
          .layoutId("icons")
          .padding(end = 4.dp)
      )
    },
    measurePolicy = { measurables, fullConstraints ->
      val picPlaceable = measurables.first { it.layoutId == "pic" }.measure(fullConstraints)
      val langPlaceable = measurables.find { it.layoutId == "lang" }?.measure(fullConstraints)

      val constraints = fullConstraints.copy(
        maxWidth = fullConstraints.maxWidth - picPlaceable.width
      )

      val iconsPlaceable = measurables.first { it.layoutId == "icons" }.measure(constraints)
      val titlePlaceable = measurables.first { it.layoutId == "title" }
        .measure(constraints.copy(maxWidth = constraints.maxWidth - iconsPlaceable.width))
      val descPlaceable = measurables.first { it.layoutId == "desc" }.measure(constraints)

      val height = max(picPlaceable.height, titlePlaceable.height + descPlaceable.height)

      layout(fullConstraints.maxWidth, height) {
        picPlaceable.placeRelative(0, 0)
        langPlaceable?.placeRelative(
          x = picPlaceable.width - langPlaceable.width,
          y = picPlaceable.height - langPlaceable.height
        )
        titlePlaceable.placeRelative(picPlaceable.width, 0)
        descPlaceable.placeRelative(picPlaceable.width, titlePlaceable.height)
        iconsPlaceable.placeRelative(
          x = constraints.maxWidth - iconsPlaceable.width + picPlaceable.width,
          y = 0
        )
      }
    }
  )
}

@Composable
private fun CatalogPic(catalog: Catalog, modifier: Modifier = Modifier) {
  when (catalog) {
    is CatalogBundled -> {
      LetterIcon(catalog.name, modifier)
    }
    else -> {
      Image(
        painter = rememberImagePainter(catalog),
        contentDescription = null,
        modifier = modifier
      )
    }
  }
}

@Composable
private fun CatalogButtons(
  catalog: Catalog,
  installStep: InstallStep?,
  onInstall: (() -> Unit)?,
  onUninstall: (() -> Unit)?,
  onPinToggle: (() -> Unit)?,
  modifier: Modifier = Modifier
) {
  Row(modifier = modifier) {
    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
      // Show either progress indicator or install button
      if (installStep != null && !installStep.isFinished()) {
        CircularProgressIndicator(
          modifier = Modifier
            .size(48.dp)
            .padding(12.dp)
        )
      } else if (onInstall != null) {
        IconButton(onClick = onInstall) {
          Icon(
            imageVector = Filled.GetApp,
            contentDescription = null
          )
        }
      }
      if (catalog !is CatalogRemote) {
        CatalogMenuButton(
          catalog = catalog,
          onPinToggle = onPinToggle,
          onUninstall = onUninstall
        )
      }
    }
  }
}

@Composable
internal fun CatalogMenuButton(
  catalog: Catalog,
  onPinToggle: (() -> Unit)?,
  onUninstall: (() -> Unit)?
) {
  var expanded by remember { mutableStateOf(false) }
  Box {
    IconButton(onClick = { expanded = true }) {
      Icon(imageVector = Icons.Filled.MoreVert, contentDescription = null)
    }
    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
      DropdownMenuItem(onClick = { /*TODO*/ }) {
        Text(localize(MR.strings.catalog_details))
      }
      if (onPinToggle != null && catalog is CatalogLocal) {
        DropdownMenuItem(onClick = onPinToggle) {
          Text(
            localize(
              if (!catalog.isPinned) MR.strings.catalog_pin else MR.strings
                .catalog_unpin
            )
          )
        }
      }
      if (onUninstall != null) {
        DropdownMenuItem(onClick = onUninstall) {
          Text(localize(MR.strings.catalog_uninstall))
        }
      }
    }
  }
}
