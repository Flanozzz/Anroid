package com.example.mvvmarchitecture2.views.changecolor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mvvmarchitecture2.R
import com.example.mvvmarchitecture2.databinding.FragmentChangeColorBinding
import com.example.foundation.views.BaseFragment
import com.example.foundation.views.BaseScreen
import com.example.foundation.views.screenViewModel
import com.example.foundation.views.HasScreenTitle

class ChangeColorFragment : BaseFragment(), HasScreenTitle {
    class Screen(
        val currentColorId: Long
    ) : BaseScreen

    override val viewModel: ChangeColorViewModel by screenViewModel()

    override fun getScreenTitle(): String? = viewModel.screenTitle.value

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChangeColorBinding.inflate(inflater, container, false)

        val adapter = ColorsAdapter(viewModel)
        setUpLayoutManager(binding, adapter)

        binding.saveButton.setOnClickListener{viewModel.onSavedPressed()}
        binding.cancelButton.setOnClickListener{viewModel.onCancelPressed()}

        viewModel.colorsList.observe(viewLifecycleOwner){
            adapter.items = it
        }
        viewModel.screenTitle.observe(viewLifecycleOwner){
            notifyScreenUpdates()
        }

        return binding.root
    }

    private fun setUpLayoutManager(binding: FragmentChangeColorBinding, adapter: ColorsAdapter){
        binding
            .colorsRecyclerView
            .viewTreeObserver
            .addOnGlobalLayoutListener(object: ViewTreeObserver.OnGlobalLayoutListener{
                override fun onGlobalLayout() {
                    binding.colorsRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val width = binding.colorsRecyclerView.width
                    val itemWidth = resources.getDimensionPixelSize(R.dimen.item_width)
                    val columns = width / itemWidth
                    binding.colorsRecyclerView.adapter = adapter
                    binding.colorsRecyclerView.layoutManager =
                        GridLayoutManager(requireContext(), columns)
                }
            })
    }
}