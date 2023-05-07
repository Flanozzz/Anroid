package com.example.mvvmarchitecture2.views.currentcolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvmarchitecture2.R
import com.example.mvvmarchitecture2.model.colors.ColorListener
import com.example.mvvmarchitecture2.model.colors.ColorsRepository
import com.example.mvvmarchitecture2.model.colors.NamedColor
import com.example.foundation.views.BaseViewModel
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions
import com.example.mvvmarchitecture2.views.changecolor.ChangeColorFragment

class CurrentColorViewModel(
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val colorsRepository: ColorsRepository
) : BaseViewModel() {
    private val _currentColor = MutableLiveData<NamedColor>()
    val currentColor: LiveData<NamedColor> = _currentColor

    private val colorListener: ColorListener = {
        _currentColor.postValue(it)
    }

    init {
        colorsRepository.addListener(colorListener)
    }

    override fun onCleared() {
        super.onCleared()
        colorsRepository.removeListener(colorListener)
    }

    override fun onResult(result: Any?) {
        super.onResult(result)
        if(result is NamedColor){
            val message = uiActions.getString(R.string.changed_color, result.name)
            uiActions.toast(message)
        }
    }

    fun changeColor(){
        val currentColor = currentColor.value ?: return
        val screen = ChangeColorFragment.Screen(currentColor.id)
        navigator.launch(screen)
    }
}