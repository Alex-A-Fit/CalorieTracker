package com.plcoding.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.core.data.preferences.Preferences
import com.plcoding.core.domain.use_case.FilterOutDigits
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
class WeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigits: FilterOutDigits
) : ViewModel() {

    var weight by mutableStateOf("180")
        private set

    var decimalWeight by mutableStateOf("00")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length <= 3) {
            this.weight = filterOutDigits(weight)
        }
    }
    fun onWeightDecimal(weight: String) {
        if (weight.length <= 2) {
            this.decimalWeight = filterOutDigits(weight)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weight = weight.toIntOrNull()
            val decimalWeight = decimalWeight.toIntOrNull()
            val isWeightCorrect = (weight != null && decimalWeight != null)
            if (!isWeightCorrect)
            {
                kotlin.run {
                    _uiEvent.send(
                        UiEvent.ShowSnackbar(
                            UiText.StringResource(R.string.error_weight_cant_be_empty)
                        )
                    )
                    return@launch
                }
            }
            val weightFloat = ("$weight.$decimalWeight").toFloat()
            preferences.saveWeight(weightFloat)
            _uiEvent.send(UiEvent.Navigate(Route.ACTIVITY))
        }
    }
}