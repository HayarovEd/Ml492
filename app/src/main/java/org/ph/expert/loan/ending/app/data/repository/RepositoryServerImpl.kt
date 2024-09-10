package org.ph.expert.loan.ending.app.data.repository

import android.util.Log
import org.ph.expert.loan.ending.app.data.mapper.mapToBaseData
import org.ph.expert.loan.ending.app.data.remote.ApiServer
import org.ph.expert.loan.ending.app.domain.model.BaseData
import org.ph.expert.loan.ending.app.domain.repository.RepositoryServer
import org.ph.expert.loan.ending.app.domain.utils.Resource
import org.ph.expert.loan.ending.app.domain.utils.Resource.Error
import org.ph.expert.loan.ending.app.domain.utils.Resource.Success
import javax.inject.Inject

class RepositoryServerImpl @Inject constructor(
    private val apiServer: ApiServer,
) : RepositoryServer {
    override suspend fun getDataDb(): Resource<BaseData> {
        return try {
            val folder = apiServer.getDataDb()
            Log.d("DATADB", "dATA DB:${folder.loanDtos.first().id}")
            Success(
                data = folder.mapToBaseData()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }

}