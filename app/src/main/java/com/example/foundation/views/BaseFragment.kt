package com.example.foundation.views

import androidx.fragment.app.Fragment
import com.example.mvvmarchitecture2.MainActivity

abstract class BaseFragment : Fragment() {
    abstract val viewModel: BaseViewModel

    fun notifyScreenUpdates(){
        (requireActivity() as MainActivity).notifyScreenUpdates()
    }
}