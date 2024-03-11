package com.example.groupassignment2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    var profileString by mutableStateOf("Visit one of the profiles in the hamburger menu for a surprise!")

    init {
        viewModelScope.launch {
        }
    }
}