package org.ph.expert.loan.ending.app

import android.app.Application
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import org.ph.expert.loan.ending.app.domain.utils.APP_METRICA
import org.ph.expert.loan.ending.app.domain.utils.MY_TRACKER

@HiltAndroidApp
class Appdfr492: Application() {
    override fun onCreate() {
        super.onCreate()

        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()


        MyTracker.initTracker(MY_TRACKER, this)
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        //UserX.init(USER_X)
    }
}