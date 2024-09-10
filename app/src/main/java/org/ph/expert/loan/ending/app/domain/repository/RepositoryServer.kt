package org.ph.expert.loan.ending.app.domain.repository

import org.ph.expert.loan.ending.app.domain.model.BaseData
import org.ph.expert.loan.ending.app.domain.utils.Resource


interface RepositoryServer {
    suspend fun getDataDb() : Resource<BaseData>
}