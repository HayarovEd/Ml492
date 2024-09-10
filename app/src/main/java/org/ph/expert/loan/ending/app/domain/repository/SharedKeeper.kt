package org.ph.expert.loan.ending.app.domain.repository

interface SharedKeeper {

    suspend fun getFireBaseToken(): String?

    suspend fun setFireBaseToken(date: String)

    suspend fun getMyTrackerInstanceId(): String?

    suspend fun setMyTrackerInstanceId(date: String)

    suspend fun getAppsFlyerInstanceId(): String?

    suspend fun setAppsFlyerInstanceId(date: String)

    suspend fun getCurrentDate(): String?

    suspend fun setCurrentDate(date: String)

    suspend fun setSub2(date: String)

    suspend fun getSub2(): String?

    suspend fun setYandexMetricaDeviceId(date: String)

    suspend fun getYandexMetricaDeviceId(): String?

    suspend fun setFirstRun(isFirst: Boolean)
    suspend fun getFirstRun(): Boolean
}