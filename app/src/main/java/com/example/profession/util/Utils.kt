package com.example.profession.util

import android.content.Context
import com.example.profession.R
import com.example.profession.data.dataSource.Param.PaymentModel

object Utils {
    fun ArabicToEnglish(str: String):String {
        var result = ""
        var en = '0'
        for (ch in str) {
            en = ch
            when (ch) {
                '۰' -> en = '0'
                '۱' -> en = '1'
                '۲' -> en = '2'
                '۳' -> en = '3'
                '۴' -> en = '4'
                '۵' -> en = '5'
                '۶' -> en = '6'
                '۷' -> en = '7'
                '۸' -> en = '8'
                '۹' -> en = '9'
            }
            result = "${result}$en"
        }
        return result
    }
    fun englishNumberToArabicNumber(number: String): String {
        val arabicNumber = mutableListOf<String>()
        for (element in number.toString()) {
            when (element) {
                '١' -> arabicNumber.add("1")
                '٢' -> arabicNumber.add("2")
                '٣' -> arabicNumber.add("3")
                '٤' -> arabicNumber.add("4")
                '٥' -> arabicNumber.add("5")
                '٦' -> arabicNumber.add("6")
                '٧' -> arabicNumber.add("7")
                '٨' -> arabicNumber.add("8")
                '٩' -> arabicNumber.add("9")
                '-' -> arabicNumber.add("-")
                else -> arabicNumber.add("0")
            }
        }
        val separator = ""

        return java.lang.String.join(separator, arabicNumber)


    }
    fun getPaymentMethod(paymrnt:Int, context:Context):PaymentModel?{
        when(paymrnt){
            Constants.CASH -> {
                return PaymentModel(paymrnt,context.resources.getString(R.string.cash), (R.drawable.money))
            }Constants.VISA -> {
            return PaymentModel(paymrnt,context.resources.getString(R.string.cash), (R.drawable.ic_visa))

            }Constants.WALLET -> {
            return PaymentModel(paymrnt,context.resources.getString(R.string.cash), (R.drawable.ic_wallet))

            }else -> {
                return  null
            }
        }

    }
}