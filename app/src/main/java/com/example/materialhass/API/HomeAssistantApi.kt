package com.example.materialhass.API

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

data class ToggleBody(val entity_id: String)

interface HomeAssistantAPI {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyOTY1Zjg2NTlmZWQ0ODhkODFiMjljMTdlYTg3ZDVmMiIsImlhdCI6MTY5OTg2NTgxMiwiZXhwIjoyMDE1MjI1ODEyfQ.EE7Z5S9-iqlxclalYtOEdlI8KmPTpJv6xfCzdAtfqg8")
    @GET("api/states")
    suspend fun getStates(): List<Entity>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyOTY1Zjg2NTlmZWQ0ODhkODFiMjljMTdlYTg3ZDVmMiIsImlhdCI6MTY5OTg2NTgxMiwiZXhwIjoyMDE1MjI1ODEyfQ.EE7Z5S9-iqlxclalYtOEdlI8KmPTpJv6xfCzdAtfqg8")
    @POST("api/services/light/toggle")
    suspend fun toggleLight(@Body body: ToggleBody)

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiIyOTY1Zjg2NTlmZWQ0ODhkODFiMjljMTdlYTg3ZDVmMiIsImlhdCI6MTY5OTg2NTgxMiwiZXhwIjoyMDE1MjI1ODEyfQ.EE7Z5S9-iqlxclalYtOEdlI8KmPTpJv6xfCzdAtfqg8")
    @GET("api/states/<entity_id>")
    suspend fun getDeviceStates(@Path("entity_id") entityId: String): Entity
}
