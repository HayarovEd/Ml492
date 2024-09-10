package org.ph.expert.loan.ending.app.data.repository

import android.app.Application
import android.content.Context
import org.ph.expert.loan.ending.app.domain.repository.SharedKeeper
import org.ph.expert.loan.ending.app.domain.utils.SHARED_APPSFLYER_INSTANCE_ID
import org.ph.expert.loan.ending.app.domain.utils.SHARED_DATA
import org.ph.expert.loan.ending.app.domain.utils.SHARED_DATE
import org.ph.expert.loan.ending.app.domain.utils.SHARED_FIREBASE_TOKEN
import org.ph.expert.loan.ending.app.domain.utils.SHARED_FIRST_RUN
import org.ph.expert.loan.ending.app.domain.utils.SHARED_MY_TRACKER_INSTANCE_ID
import org.ph.expert.loan.ending.app.domain.utils.SHARED_SUB2
import org.ph.expert.loan.ending.app.domain.utils.SHARED_YANDEX_DEVICE_ID
import javax.inject.Inject

class SharedKeeperImpl @Inject constructor(
    application: Application
): SharedKeeper {
    private val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)

    override suspend fun getFireBaseToken(): String? = sharedPref.getString(SHARED_FIREBASE_TOKEN, "")

    override suspend fun setFireBaseToken(date: String) =
        sharedPref.edit().putString(SHARED_FIREBASE_TOKEN, date).apply()

    override suspend fun getMyTrackerInstanceId(): String? = sharedPref.getString(
        SHARED_MY_TRACKER_INSTANCE_ID, "")

    override suspend fun setMyTrackerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_MY_TRACKER_INSTANCE_ID, date).apply()

    override suspend fun getAppsFlyerInstanceId(): String? = sharedPref.getString(
        SHARED_APPSFLYER_INSTANCE_ID, "")

    override suspend fun setAppsFlyerInstanceId(date: String) =
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, date).apply()

    override suspend fun getCurrentDate(): String? = sharedPref.getString(SHARED_DATE, "")

    override suspend fun setCurrentDate(date: String) =
        sharedPref.edit().putString(SHARED_DATE, date).apply()

    override suspend fun setSub2(date: String) =
        sharedPref.edit().putString(SHARED_SUB2, date).apply()

    override suspend  fun getSub2(): String? = sharedPref.getString(SHARED_SUB2, "")

    override suspend fun setYandexMetricaDeviceId(date: String) =
        sharedPref.edit().putString(SHARED_YANDEX_DEVICE_ID, date).apply()

    override suspend fun getYandexMetricaDeviceId(): String? = sharedPref.getString(
        SHARED_YANDEX_DEVICE_ID, "")


    override suspend fun setFirstRun(isFirst: Boolean) =
        sharedPref.edit().putBoolean(SHARED_FIRST_RUN, isFirst).apply()

    override suspend fun getFirstRun(): Boolean = sharedPref.getBoolean(SHARED_FIRST_RUN, true)
}