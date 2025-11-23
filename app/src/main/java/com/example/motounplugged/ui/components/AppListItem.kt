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
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.drawablepainter.rememberDrawablePainter



@Composable
fun AppListItem(
    appInfo: AppInfo,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val packageManager = context.packageManager
    val icon = remember(appInfo.packageName) {
        try {
            packageManager.getApplicationIcon(appInfo.packageName)
        } catch (e: Exception) {
            null
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isSelected) }
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Image(
                painter = rememberDrawablePainter(drawable = it),
                contentDescription = "${appInfo.name} icon",
                modifier = Modifier.size(40.dp)
            )
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
