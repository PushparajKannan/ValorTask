package com.example.valortask.view.activity.users_module.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.valortask.R
import com.example.valortask.baseclass.BaseViewState
import com.example.valortask.databinding.ActivityHomeBinding
import com.example.valortask.databinding.NavHeaderMainBinding
import com.example.valortask.view.activity.authentication_module.view.LoginActivity
import com.example.valortask.view.activity.users_module.viewmodel.HomeActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModels<HomeActivityViewModel>()


    private lateinit var binding: ActivityHomeBinding

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController: NavController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navHostFragment.navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        setSupportActionBar(binding.appBarMain.toolbar)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

         appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_product_list, R.id.navigation_cart, R.id.navigation_payment, R.id.navigation_profile,R.id.nav_logout

            ), binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.appBarMain.contentMain.navView.setupWithNavController(navController)
        binding.leftNavView.setupWithNavController(navController)
        liveDataObserver()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.savedCardFragment , R.id.nav_setting -> {
                    binding.appBarMain.contentMain.navView.visibility = View.GONE
                }
                else ->{
                    binding.appBarMain.contentMain.navView.visibility = View.VISIBLE
                }
            }

        }
    }


    fun liveDataObserver(){
        val headerView: View = binding.leftNavView.getHeaderView(0)
        val headerBinding: NavHeaderMainBinding = NavHeaderMainBinding.bind(headerView)
        viewModel.loggedInUserInfo.observe(this){
            Log.e("Data","-->${it?.phoneNumber}")
            headerBinding.tvUserName.text = it?.userName ?: ""
            headerBinding.tvUserMail.text = it?.userMail ?: ""
          //  binding.leftNavView
        }



        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    when (it) {

                        is BaseViewState.UserLogOut -> {
                            withContext(Dispatchers.Main){
                                val intent = Intent(applicationContext, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        }
                    }

                }


            }
        }



    }



    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(Gravity.LEFT)
        }else{
            super.onBackPressed()
        }


    }
}