package xyz.scoca.assignment8.model.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("data")
    val user: List<User>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)