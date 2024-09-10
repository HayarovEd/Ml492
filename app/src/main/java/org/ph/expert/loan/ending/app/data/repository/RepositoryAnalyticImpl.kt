package org.ph.expert.loan.ending.app.data.repository

import android.util.Log
import com.google.gson.Gson
import org.ph.expert.loan.ending.app.data.remote.ApiAnalytic
import org.ph.expert.loan.ending.app.data.remote.dto.AffSub1
import org.ph.expert.loan.ending.app.data.remote.dto.AffSub2
import org.ph.expert.loan.ending.app.data.remote.dto.AffSub3
import org.ph.expert.loan.ending.app.data.remote.dto.AffSub5
import org.ph.expert.loan.ending.app.data.remote.dto.Sub1
import org.ph.expert.loan.ending.app.data.remote.dto.Sub2
import org.ph.expert.loan.ending.app.data.remote.dto.Sub3
import org.ph.expert.loan.ending.app.data.remote.dto.Sub5
import org.ph.expert.loan.ending.app.domain.repository.RepositoryAnalytic
import org.ph.expert.loan.ending.app.domain.utils.Resource
import org.ph.expert.loan.ending.app.domain.utils.Resource.Success
import org.ph.expert.loan.ending.app.domain.utils.Resource.Error
import javax.inject.Inject


class RepositoryAnalyticImpl @Inject constructor(
    private val apiAnalytic: ApiAnalytic
): RepositoryAnalytic {

    override suspend fun getSub1(
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): Resource<Sub1> {
        return try {
            val jsonData = apiAnalytic.getSub1(
                AffSub1(
                    applicationToken = applicationToken,
                    userId = userId,
                    payloadAffsub = createPayloadAffsub1(
                        myTrackerId = myTrackerId,
                        appMetricaId = appMetricaId,
                        appsflyer = appsflyer,
                        firebaseToken = firebaseToken
                    )
                )
            )
            val gson = Gson()
            Success(
                data = gson.fromJson(jsonData, Sub1::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getSub2(
        applicationToken: String,
        userId: String,
        appsflyer: String,
        myTracker: String
    ): Resource<Sub2> {
        return try {
            val jsonData = apiAnalytic.getSub2(
                AffSub2(
                    applicationToken = applicationToken,
                    userId = userId,
                    appsflyer = appsflyer,
                    myTracker = myTracker
                )
            )
            val gson = Gson()
            Success(
                data = gson.fromJson(jsonData, Sub2::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getSub3(
        applicationToken: String,
        userId: String,
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        token: String
    ): Resource<Sub3> {
        return try {
            Log.i("TEST UPLOAD DATA", "getSub3 getHmsToken:$token")
            Log.d ("TEST UPLOAD DATA", "userId $userId")
            val a = createPayloadAffsub3(
                myTrackerId = myTrackerId,
                appMetricaId = appMetricaId,
                appsflyer = appsflyer,
                token = token
            )
            Log.d ("TEST UPLOAD DATA", "createPayloadAffsub3 $a")

            val jsonData = apiAnalytic.getSub3(
                AffSub3(
                    applicationToken = applicationToken,
                    userId = userId,
                    payloadAffsub = a
                )
            )
            val gson = Gson()
            Success(
                data = gson.fromJson(jsonData, Sub3::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }

    override suspend fun getSub5(
        applicationToken: String,
        userId: String,
        gaid: String
    ): Resource<Sub5> {
        Log.d ("TEST UPLOAD DATA", "gaid $gaid")
        return try {
            val jsonData = apiAnalytic.getSub5(
                AffSub5(
                    applicationToken = applicationToken,
                    userId = userId,
                    payloadAffsub = createPayloadAffsub5(
                        gaid = gaid
                    )
                )
            )
            val gson = Gson()
            Success(
                data = gson.fromJson(jsonData, Sub5::class.java)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Error(e.message ?: "An unknown error")
        }
    }

    private fun createPayloadAffsub1(
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        firebaseToken: String
    ): String =
        "{\"AppMetricaDeviceID\":\"$appMetricaId\",\"Appsflyer\":\"$appsflyer\",\"FireBase\":\"$firebaseToken\",\"MyTracker\":\"$myTrackerId\"}"

    private fun createPayloadAffsub3(
        myTrackerId: String,
        appMetricaId: String,
        appsflyer: String,
        token: String
    ): String =
        "{\"FireBaseToken\":\"${token}\"}"
        //"{\"AppMetricaDeviceID\":\"$appMetricaId\",\"Appsflyer\":\"$appsflyer\",\"FireBaseToken\":\"${firebaseToken}\",\"MyTracker\":\"$myTrackerId\"}"


    private fun createPayloadAffsub5(
        gaid: String
    ): String =
        "{\"OAID\":\"$gaid\"}"
}
