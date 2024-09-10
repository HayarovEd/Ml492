package org.ph.expert.loan.ending.app.data.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.huawei.hms.aaid.HmsInstanceId
import com.huawei.hms.ads.identifier.AdvertisingIdClient
import com.huawei.hms.common.ApiException
import com.huawei.hms.push.HmsMessaging
import org.ph.expert.loan.ending.app.domain.repository.Service
import com.my.tracker.MyTracker
import com.yandex.metrica.AppMetricaDeviceIDListener
import com.yandex.metrica.YandexMetrica
import org.ph.expert.loan.ending.app.domain.utils.APP_ID_HMS
import kotlinx.coroutines.delay

import java.io.IOException
import javax.inject.Inject

class ServiceImpl @Inject constructor(
    private val application: Application,) : Service {

    //P9
    override val instanceIdMyTracker: String = MyTracker.getInstanceId(application)

    override suspend fun getOAID(): String? {
        return try {
            val adInfo = AdvertisingIdClient.getAdvertisingIdInfo(application)
            Log.i("RTYUE", "adInfo:${adInfo.id}")
            return adInfo.id
        } catch (e: IOException) {
            Log.i("RTYUE", "OAID IOException:$e")
            null
        }
    }


    override suspend fun getHmsToken() : String? {
        return try {
            var token = ""
            while (token.isBlank()) {
                token = HmsInstanceId.getInstance(application)
                    .getToken(APP_ID_HMS, HmsMessaging.DEFAULT_TOKEN_SCOPE)
                Log.i("RTYUE", "getHmsToken:$token")
                delay(1000)
            }
            token
        } catch (e: ApiException) {
            Log.i("RTYUE", "hmw error:$e")
            null
        }
    }


    override fun checkedInternetConnection(): Boolean {
        var result = false
        val connectivityManager =
            application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }


    override fun sendAppsFlyerEvent(key: String, content:Map<String, String>) {
        AppsFlyerLib.getInstance().logEvent(application, key, content)
    }

    override fun getYandexMetricaDeviceId (callback: (String?) -> Unit) {
        YandexMetrica.requestAppMetricaDeviceID(object : AppMetricaDeviceIDListener {
            override fun onLoaded(deviceId: String?) {
                Log.d("TEST APPS FLYER", "yandex deviceId -  $deviceId")
                callback(deviceId)
            }

            override fun onError(p0: AppMetricaDeviceIDListener.Reason) {

            }

        })
    }

}