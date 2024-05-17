package com.example.myapplication.features.main

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor() : ViewModel(){
    var rounds = mutableIntStateOf(3)
}