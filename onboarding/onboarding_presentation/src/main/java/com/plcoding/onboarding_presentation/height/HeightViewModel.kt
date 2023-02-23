package com.plcoding.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.data.preferences.Preferences
import com.plcoding.core.domain.use_case.FilterOutDigits
import com.plcoding.core.domain.use_case.FilterOutHeightInches
import com.plcoding.core.navigation.Route
import com.plcoding.core.util.UiEvent
import com.plcoding.core.util.UiText
import com.plcoding.onboarding_presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits,
    private val filterOutHeightInches: FilterOutHeightInches
) : ViewModel() {

    var heightFeet by mutableStateOf("5")
        private set

    var heightInches by mutableStateOf("7")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightForFeetEnter(height: String) {
        if (height.length <= 1) {
            this.heightFeet = filterOutDigits(height)
        }
    }

    fun onHeightForInchesEnter(height: String) {
        if (height.length <= 2) {
            this.heightInches = filterOutHeightInches(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumberForFeet = heightFeet.toIntOrNull()
            val heightNumberForInches = heightInches.toIntOrNull()
            if (heightNumberForFeet == null || heightNumberForInches == null) {
                kotlin.run {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_height_cant_be_empty)
                        )
                    )
                    return@launch
                }
            }
            preferences.saveHeightFeet(heightNumberForFeet)
            preferences.saveHeightInches(heightNumberForInches)
            _uiEvent.send(UiEvent.Navigate(Route.WEIGHT))
        }
    }
}