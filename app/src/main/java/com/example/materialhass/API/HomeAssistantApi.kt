package com.example.materialhass.API

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

data class ToggleBody(val entity_id: String)
data class TurnOnWithBrightnessBody(
    val entity_id: String,
    val brightness: Int
)

const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyOTY1Zjg2NTlmZWQ0ODhkODFiMjljMTdlYTg3ZDVmMiIsImlhdCI6MTY5OTg2NTgxMiwiZXhwIjoyMDE1MjI1ODEyfQ.EE7Z5S9-iqlxclalYtOEdlI8KmPTpJv6xfCzdAtfqg8"
interface HomeAssistantAPI {
    @Headers("Authorization: Bearer ${token}")
    @GET("api/states")
    suspend fun getStates(): List<Entity>

    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/light/toggle")
    suspend fun toggleLight(@Body body: ToggleBody)

    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/light/turn_on")
    suspend fun lightBrightness(@Body body: TurnOnWithBrightnessBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/light/toggle")
    suspend fun toggleHvac(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/cover/open_cover")
    suspend fun coverOpen(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/cover/close_cover")
    suspend fun coverClose(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/cover/stop_cover")
    suspend fun coverStop(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/cover/open_cover")
    suspend fun openCover(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/cover/close_cover")
    suspend fun closeCover(@Body body: ToggleBody)

    @Headers("Authorization: Bearer ${token}")
    @GET("api/states/{entity_id}")
    suspend fun getDeviceStates(@Path("entity_id") entityId: String): Entity
}
