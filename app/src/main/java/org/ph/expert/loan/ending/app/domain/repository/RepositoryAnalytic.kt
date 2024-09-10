package org.ph.expert.loan.ending.app.domain.repository

import org.ph.expert.loan.ending.app.data.remote.dto.Sub1
import org.ph.expert.loan.ending.app.data.remote.dto.Sub2
import org.ph.expert.loan.ending.app.data.remote.dto.Sub3
import org.ph.expert.loan.ending.app.data.remote.dto.Sub5
import org.ph.expert.loan.ending.app.domain.utils.Resource


interface RepositoryAnalytic {
    suspend fun getSub1 (
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<Sub1>

    suspend fun getSub2 (
        applicationToken: String,
        userId: String,
        appsflyer: String,
        myTracker: String,
    ): Resource<Sub2>

    suspend fun getSub3 (
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        token: String,
    ): Resource<Sub3>

    suspend fun getSub5 (
        applicationToken: String,
        userId: String,
        gaid:String
    ): Resource<Sub5>
}