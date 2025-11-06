package com.example.motounplugged.database.entities

import androidx.room.Embedded
import androidx.room.Relation

data class ProfileBlockedApps(
    @Embedded val profile: ProfilesEntity,

    @Relation(
        parentColumn = "idProfile",
        entityColumn = "blockedProfileId"
    )

    val blockedApps: List<BlockedAppsEntity>

)
