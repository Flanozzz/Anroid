package com.example.mvvmarchitecture2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.foundation.ActivityScopeViewModel
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.HasScreenTitle
import com.example.mvvmarchitecture2.views.currentcolor.CurrentColorFragment

class MainActivity : AppCompatActivity() {

    private val activityViewModel: ActivityScopeViewModel by
    viewModels{ViewModelProvider.AndroidViewModelFactory(application)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setContentView(R.layout.activity_main)
        if(savedInstanceState == null){
            activityViewModel.launchFragment(
                activity = this,
                screen = CurrentColorFragment.Screen(),
                addToBackStack = false
            )
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCallbacks, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCallbacks)
        super.onDestroy()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onResume() {
        super.onResume()
        activityViewModel.whenActivityActive.resource = this
    }

    override fun onPause() {
        super.onPause()
        activityViewModel.whenActivityActive.resource = null
    }

    fun notifyScreenUpdates(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

        if(supportFragmentManager.backStackEntryCount > 0){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        else{
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }

        if(currentFragment is HasScreenTitle && currentFragment.getScreenTitle() != null){
            supportActionBar?.title = currentFragment.getScreenTitle()
        }
        else{
            supportActionBar?.title = getString(R.string.app_name)
        }

        val result = activityViewModel.result.value?.getValue() ?: return
        if(currentFragment is BaseFragment){
            currentFragment.viewModel.onResult(result)
        }
    }

    private val fragmentCallbacks = object : FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            notifyScreenUpdates()
        }
    }
}