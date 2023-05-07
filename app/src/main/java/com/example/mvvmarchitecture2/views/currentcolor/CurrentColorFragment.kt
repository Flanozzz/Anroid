package com.example.mvvmarchitecture2.views.currentcolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mvvmarchitecture2.databinding.FragmentCurrentColorBinding
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel

class CurrentColorFragment : BaseFragment() {
    class Screen : BaseScreen

    override val viewModel: CurrentColorViewModel by screenViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCurrentColorBinding.inflate(inflater, container, false)

        viewModel.currentColor.observe(viewLifecycleOwner){
            binding.colorView.setBackgroundColor(it.value)
        }

        binding.changeColorButton.setOnClickListener{
            viewModel.changeColor()
        }
        return binding.root
    }
}