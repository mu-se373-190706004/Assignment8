package xyz.scoca.assignment8.model.crud


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("user")
    val user: UserData
)