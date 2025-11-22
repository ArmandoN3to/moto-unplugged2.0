// ui/components/AppListItem.kt
package com.example.motounplugged.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.motounplugged.models.AppInfo
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import android.graphics.drawable.BitmapDrawable
import androidx.compose.runtime.remember


@Composable
fun AppListItem(
    appInfo: AppInfo,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current

    val iconDrawable = remember(appInfo.packageName) {
        try {
            context.packageManager.getApplicationIcon(appInfo.packageName)
        } catch (_: Exception) {
            null
        }
    }

    val painter = remember(iconDrawable) {
        when (iconDrawable) {
            is android.graphics.drawable.BitmapDrawable ->
                androidx.compose.ui.graphics.painter.BitmapPainter(
                    iconDrawable.bitmap.asImageBitmap()
                )
            else -> null
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isSelected) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (painter != null) {
            androidx.compose.foundation.Image(
                painter = painter,
                contentDescription = "${appInfo.name} icon",
                modifier = Modifier.size(40.dp)
            )
        } else {
            Box(modifier = Modifier.size(40.dp))
        }

        Spacer(Modifier.width(16.dp))

        Text(
            text = appInfo.name,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}