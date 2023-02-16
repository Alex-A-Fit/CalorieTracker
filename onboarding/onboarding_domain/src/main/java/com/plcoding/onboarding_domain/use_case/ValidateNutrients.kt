package com.plcoding.onboarding_domain.use_case

import com.plcoding.core.util.UiText
import com.plcoding.onboarding_domain.R

class ValidateNutrients {
    operator fun invoke(
        carbsRatio: String,
        proteinRatio: String,
        fatRatio: String
    ): Result {
        val carbsRatioInt = carbsRatio.toIntOrNull()
        val proteinRatioInt = proteinRatio.toIntOrNull()
        val fatRatioInt = fatRatio.toIntOrNull()
        if (carbsRatioInt == null || proteinRatioInt == null || fatRatioInt == null) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_invalid_values)
            )
        }
        if (carbsRatioInt + proteinRatioInt + fatRatioInt != 100) {
            return Result.Error(
                message = UiText.StringResource(R.string.error_not_100_percent)
            )
        }
        return Result.Success(
            carbsRatio = carbsRatioInt / 100f,
            proteinRatio = proteinRatioInt / 100f,
            fatRatio = fatRatioInt / 100f
        )
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinRatio: Float,
            val fatRatio: Float
        ) : Result()

        data class Error(val message: UiText) : Result()
    }
}