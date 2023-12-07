package com.example.valortask.view.activity.authentication_module.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.valortask.R
import com.example.valortask.databinding.ActivitySplashScreenBinding
import com.example.valortask.utilities.SharedPref
import com.example.valortask.view.activity.users_module.view.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {
    private val mDelayHandler: Handler = Handler(Looper.getMainLooper())
    private val splashDelay: Long = 2000 //2 seconds


    @set:Inject
    var sharedPref: SharedPref? =null

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {

            if( sharedPref?.isUerLoggedIn == true){
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
            }
            finish()



        }

    }

    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)
        mDelayHandler.postDelayed(mRunnable, splashDelay)
    }

    public override fun onDestroy() {
        mDelayHandler.removeCallbacks(mRunnable)
        super.onDestroy()
    }

}