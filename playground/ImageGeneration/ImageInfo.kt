package com.example.ImageGeneration

import kotlinx.serialization.Serializable

@Serializable
data class ImageInfo(val code : String, val settings : SettingsInfo)

@Serializable
data class SettingsInfo(val language : String, val theme : String)