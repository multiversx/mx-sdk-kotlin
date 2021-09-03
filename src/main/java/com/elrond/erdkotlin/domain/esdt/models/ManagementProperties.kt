package com.elrond.erdkotlin.domain.esdt.models

enum class ManagementProperty(val serializedName: String) {
    CanMint("canMint"),
    CanBurn("canBurn"),
    CanPause("canPause"),
    CanFreeze("canFreeze"),
    CanWipe("canWipe"),
    CanAddSpecialRoles("canAddSpecialRoles"),
    CanChangeOwner("canChangeOwner"),
    CanUpgrade("canUpgrade")
}
