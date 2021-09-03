package com.elrond.erdkotlin.domain.esdt.models

enum class EsdtSpecialRole {

    // ESDT
    ESDTRoleLocalBurn,
    ESDTRoleLocalMint,

    // NFT & SFT
    ESDTRoleNFTCreate,
    ESDTRoleNFTBurn,

    // SFT only
    ESDTRoleNFTAddQuantity;
}
