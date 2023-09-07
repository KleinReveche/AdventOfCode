package com.kleinreveche.adventofcode

import com.kleinreveche.adventofcode.twentytwo.*

fun main() {
    println("Hello World!")
    println("Welcome to the 2022 Advent of Code!")

    val showMore = false

    dayOne()
    dayTwo()
    dayThree()
    dayFour()
    dayFive(showMore)
    daySix()
}

object Utils {
    fun readInput(year: String, day: String): String?
    {
        val resourceName = "/inputs/$year/$day.txt" // Adjust the resource path accordingly
        val inputStream = this::class.java.getResourceAsStream(resourceName)

        if (inputStream != null) {
            return inputStream.bufferedReader().use { it.readText() }
        }

        return null
    }
}