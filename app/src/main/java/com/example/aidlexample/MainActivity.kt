package com.example.aidlexample

import SepratePackage.IAidlInterface
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.aidlexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var aidlObject: IAidlInterface
    private var TAG = "android_aidl_example"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bingToAidlService();


        binding.AddNumbers.setOnClickListener {
            var result = aidlObject.addNumbers(Integer.parseInt(binding.editFirstNumber.toString()), Integer.parseInt(binding.editSecondtNumber.toString()))
            binding.edResult.setText(result)
        }

    }

    fun bingToAidlService(){

       var aidlServiceIntent = Intent("connection_to_aild_service")
        var explicitIntent = convertImplicitIntentToExplicitIntent(aidlServiceIntent, this)
        bindService(explicitIntent,mConnection, BIND_AUTO_CREATE)
    }


    fun convertImplicitIntentToExplicitIntent(implicitIntent: Intent?, context: Context): Intent? {
        val pm: PackageManager = context.getPackageManager()
        val resolveInfoList = pm.queryIntentServices(
            implicitIntent!!, 0
        )
        if (resolveInfoList == null || resolveInfoList.size != 1) {
            return null
        }
        val serviceInfo = resolveInfoList[0]
        val component =
            ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
        val explicitIntent = Intent(implicitIntent)
        explicitIntent.component = component
        return explicitIntent
    }


    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
          //  SepratePackage.IAidlInterface.Stub(service)
           aidlObject =  SepratePackage.IAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }

    }



    fun addNumbers(firstNumber: Int, seconfNumber:Int): Int {
        return firstNumber + seconfNumber

    }
}