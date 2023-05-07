package com.example.mvvmarchitecture2.views.changecolor

import com.example.mvvmarchitecture2.model.colors.NamedColor

data class NamedColorListItem(
    val namedColor: NamedColor,
    val selected: Boolean
) {
}