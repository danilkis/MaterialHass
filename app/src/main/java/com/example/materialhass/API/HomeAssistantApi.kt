package com.example.materialhass.API

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

data class ToggleBody(val entity_id: String)
    data class TemplateBody(val template: String)
data class TurnOnWithBrightnessBody(
    val entity_id: String,
    val brightness: Int
)

data class PositionBody(
    val entity_id: String,
    val position: Int
)

data class TempBody(
    val entity_id: String,
    val temperature: Double
)
const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI0MDZhMWNlOTJlMTM0MTYwYTJkM2QxN2NmNzdmMjBkMSIsImlhdCI6MTcwNDE5MTc2MCwiZXhwIjoyMDE5NTUxNzYwfQ.05MtZU0U25fO8R83fjlssM4INjVXJCoOHAamF3Q5tiI"
interface HomeAssistantAPI {
    @Headers("Authorization: Bearer ${token}")
    @GET("api/states")
    suspend fun getStates(): List<Entity>

    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/light/toggle")
    suspend fun toggleLight(@Body body: ToggleBody)

    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/switch/toggle")
    suspend fun toggleSwitch(@Body body: ToggleBody)

    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/light/turn_on")
    suspend fun lightBrightness(@Body body: TurnOnWithBrightnessBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/climate/turn_on")
    suspend fun onHvac(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/climate/turn_off")
    suspend fun offHvac(@Body body: ToggleBody)
    @Headers("Authorization: Bearer ${token}")
    @POST("api/services/climate/set_temperature")
    suspend fun setTemperature(@Body body: TempBody)
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
    @POST("api/services/cover/set_cover_position")
    suspend fun setCoverPosition(@Body body: PositionBody)

    @Headers("Authorization: Bearer ${token}")
    @POST("api/template")
    suspend fun getRooms(@Body body: TemplateBody): List<String>

    @Headers("Authorization: Bearer ${token}")
    @POST("api/template")
    suspend fun getRoomDevices(@Body body: TemplateBody): List<String>

    @Headers("Authorization: Bearer ${token}")
    @GET("api/states/{entity_id}")
    suspend fun getDeviceStates(@Path("entity_id") entityId: String): Entity
}
