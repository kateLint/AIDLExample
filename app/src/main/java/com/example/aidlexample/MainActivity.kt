package com.example.aidlexample

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.example.aidlexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var TAG = "android_aidl_example"
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNumbers( Integer.parseInt(binding.editFirstNumber.toString()), Integer.parseInt(binding.editSecondtNumber.toString()))

        bingToAidlService();

    }

    fun bingToAidlService(){

       var aidlServiceIntent = Intent("connection_to_aild_service")

        bindService(aidlServiceIntent,mConnection, BIND_AUTO_CREATE)
    }

    private val mConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SepratePackage.IAidlInterface.Stub(service)
        }

    }



    fun addNumbers(firstNumber: Int, seconfNumber:Int): Int {
        return firstNumber + seconfNumber

    }
}