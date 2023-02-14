package com.plcoding.core.domain.use_case

class FilterOutHeightInches {
    operator fun invoke(inches: String): String {
        return when(inches.isBlank()){
            true -> inches.trim()
            false -> {
                val validInches = inches.toIntOrNull() ?: inches.dropLast(1)
                if (validInches in 0..11) inches else inches.dropLast(1)
            }
        }
    }
}