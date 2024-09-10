package org.ph.expert.loan.ending.app

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.google.gson.Gson
import com.my.tracker.MyTracker
import dagger.hilt.android.AndroidEntryPoint
import org.ph.expert.loan.ending.app.domain.utils.APPS_FLYER
import org.ph.expert.loan.ending.app.domain.utils.LINK
import org.ph.expert.loan.ending.app.domain.utils.SHARED_APPSFLYER_INSTANCE_ID
import org.ph.expert.loan.ending.app.domain.utils.SHARED_DATA
import org.ph.expert.loan.ending.app.ui.BaseScene
import org.ph.expert.loan.ending.app.ui.state.MainViewModel
import org.ph.expert.loan.ending.app.ui.theme.Ml492Theme
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val requestPermissionLauncherFireBase = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            Toast.makeText(
                this,
                "FCM can't post notifications without POST_NOTIFICATIONS permission",
                Toast.LENGTH_LONG,
            ).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askNotificationPermission()
        intent.extras?.let {
            for (key in it.keySet()) {
                val value = intent.extras?.get(key)
                if (key== LINK) {
                    viewModel.loadLink(
                        link = value.toString(),
                    )
                }
            }
        }

        MyTracker.setAttributionListener {
            Log.d("ASDFGH", "myTracker activity $it")
            viewModel.loadMTDeeplink(
                deeplink = it.deeplink
            )
        }

        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val gson = Gson()
                val appsFlayer = gson.toJson(conversionData)
                //conversionData.entries.joinToString(separator = ", ") { "${it.key}=${it.value}" }
                Log.d("TEST APPS FLYER", "appsFlayer -  $appsFlayer")
                viewModel.loadAFDeeplink(appsFlayer)
            }

            override fun onConversionDataFail(error: String?) {
                Log.d("TEST APPS FLYER", "data fail conversion error $error")
            }

            override fun onAppOpenAttribution(attributionData: MutableMap<String, String>?) {
                attributionData?.forEach {
                    Log.d("TEST APPS FLYER", "attribution key ${it.key} valur ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                Log.d("TEST APPS FLYER", "attribution failure conversion error $error")
            }
        }
        val instanceId = AppsFlyerLib.getInstance().getAppsFlyerUID(this)
        Log.d("TEST APPS FLYER", "instanceId $instanceId")
        val sharedPref = application.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        sharedPref.edit().putString(SHARED_APPSFLYER_INSTANCE_ID, instanceId).apply()

        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)
        enableEdgeToEdge()
        setContent {
            BaseScene(viewModel = viewModel)
        }
    }
    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
            } else {
                requestPermissionLauncherFireBase.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}
