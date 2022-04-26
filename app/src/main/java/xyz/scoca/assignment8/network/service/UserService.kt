package xyz.scoca.assignment8.network.service

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import xyz.scoca.assignment8.model.auth.AuthResponse
import xyz.scoca.assignment8.model.crud.User


interface UserService {

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Call<AuthResponse>

    @FormUrlEncoded
    @POST("register.php")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("gender") gender: String
    ) : Call<AuthResponse>

    @FormUrlEncoded
    @POST("update.php")
    fun updateUser(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("gender") gender: String
    ) : Call<User>

    @FormUrlEncoded
    @POST("delete.php")
    fun deleteUser(
        @Field("email") email: String,
    ) : Call<User>

    @FormUrlEncoded
    @POST("fetch_user.php")
    fun fetchUser(
        @Field("email") email: String,
    ) : Call<User>

}