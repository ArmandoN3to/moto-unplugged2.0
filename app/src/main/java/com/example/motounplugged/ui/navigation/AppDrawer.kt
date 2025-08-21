// AppDrawer.kt
package com.example.motounplugged.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    selectedItem: String,
    onItemSelected: (NavigationItem) -> Unit
) {
    ModalDrawerSheet {
        Spacer(Modifier.height(16.dp))
        DrawerItems.Topitems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.title) },
                selected = selectedItem == item.route,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )

        }
        Spacer(Modifier.height(450.dp))
        HorizontalDivider(Modifier.padding(start = 24.dp , end = 24.dp))
        DrawerItems.BottomItems.forEach { item ->
            NavigationDrawerItem(

                label = { Text(item.title) },
                selected = selectedItem == item.route,
                onClick = { onItemSelected(item) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },

                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
        }
    }
}
