package com.example.foundation.uiactions

interface UIActions{
    fun toast(message: String)
    fun getString(messageRes: Int, vararg args: Any): String
}