package com.plcoding.core.domain.use_case

class FilterOutWeight {

    operator fun invoke(text: String): String {
        var numericalWeightValue = text.filter { it.isDigit() }
        if (numericalWeightValue.length >= 4){
            numericalWeightValue = checkForDecimal(numericalWeightValue)
            numericalWeightValue = checkForDecimalDigits(numericalWeightValue)
        }
        return numericalWeightValue
    }

    private fun checkForDecimal(weight: String): String{
        return if (weight.contains(".")) weight else{
            val lastDigits = weight.drop(3)
            val firstThreeDigits = weight.dropLast(weight.length - 3)
            "$firstThreeDigits.$lastDigits"
        }
    }

    private fun checkForDecimalDigits(weight: String) : String {
        val dotIndex = weight.indexOf(".")
        val numbersPriorToDotIndex = weight.substring(0, dotIndex)
        var numbersAfterDotIndex = weight.substringAfter(".")
        if (numbersAfterDotIndex.length > 2) numbersAfterDotIndex = numbersAfterDotIndex.dropLast(1)
        return "$numbersPriorToDotIndex.$numbersAfterDotIndex"
    }
}