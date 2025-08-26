package com.example.motounplugged.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun Icon_Edit_Profile(
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))

            //.background(Color(0xFFEEEEEE))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    )
    {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = "Editar",
            modifier = modifier
                .size(16.dp)
                .clickable {
                    println("Clicou no icon")
                }
        )



    }

}

@Preview(showBackground = true)
@Composable
fun Icon_ProfilePreview() {
    Column (
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon_Edit_Profile()

    }

}