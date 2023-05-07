package com.example.mvvmarchitecture2.views.changecolor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.mvvmarchitecture2.R
import com.example.mvvmarchitecture2.model.colors.ColorsRepository
import com.example.mvvmarchitecture2.model.colors.NamedColor
import com.example.foundation.views.BaseViewModel
import com.example.foundation.navigator.Navigator
import com.example.foundation.uiactions.UIActions

class ChangeColorViewModel(
    screen: ChangeColorFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UIActions,
    private val colorsRepository: ColorsRepository,
    savedSateHandle: SavedStateHandle
) : BaseViewModel(), ColorsAdapter.Listener {

    private val _availableColors = MutableLiveData<List<NamedColor>>()
    private val _currentColorId = savedSateHandle.getLiveData("currentColorId", screen.currentColorId)

    private val _colorsList = MediatorLiveData<List<NamedColorListItem>>()
    val colorsList: LiveData<List<NamedColorListItem>> = _colorsList

    private val _screenTitle = MutableLiveData<String>()
    val screenTitle: LiveData<String> = _screenTitle

    init {
        _availableColors.value = colorsRepository.getAvailableColors()

        _colorsList.addSource(_availableColors){mergeSources()}
        _colorsList.addSource(_currentColorId){mergeSources()}
    }

    private fun mergeSources(){
        val colors = _availableColors.value ?: return
        val currentColorId = _currentColorId.value ?: return
        val currentColor = colors.first {it.id == currentColorId}
        _colorsList.value = colors.map{ NamedColorListItem(it, currentColorId == it.id) }
        _screenTitle.value = uiActions.getString(R.string.change_color_screen_title, currentColor.name)
    }

    override fun onColorChosen(namedColor: NamedColor) {
        _currentColorId.value = namedColor.id
    }

    fun onSavedPressed(){
        val currentColorId = _currentColorId.value ?: return
        val currentColor = colorsRepository.getById((currentColorId))

        colorsRepository.currentColor = currentColor
        navigator.goBack(result = currentColor)
    }

    fun onCancelPressed(){
        navigator.goBack()
    }
}