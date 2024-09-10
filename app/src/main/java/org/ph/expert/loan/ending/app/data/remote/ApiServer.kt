package org.ph.expert.loan.ending.app.data.remote

import org.ph.expert.loan.ending.app.data.remote.dto.server_data.BaseDto
import retrofit2.http.GET

interface ApiServer {
    @GET ("492/db.json")
    suspend fun getDataDb () : BaseDto
}