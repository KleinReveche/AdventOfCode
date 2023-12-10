package com.kleinreveche.adventofcode.twentythree.day01

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyThree, day = "Day01", name = "Trebuchet?!")
class Solution(private val input: String) : ISolution {
    override fun partOne(): Any {
        val solution = solve("1|2|3|4|5|6|7|8|9")
        return "The sum of all of the calibration values is $solution"
    }

    override fun partTwo(): Any {
        val solution = solve("one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9")
        return "The sum of all of the calibration values with numbers spelt is $solution"
    }

    private fun solve(pattern: String): Int {
        val data = input.trim().lines()
        val calibrations = mutableListOf<Int>()

        for (line in data) {
            var regex = Regex(pattern)
            val firstMatch = regex.find(line)?.value
            regex = Regex(pattern.reversed())
            val lastMatch = regex.find(line.reversed())?.value?.reversed()
            calibrations.add("${firstMatch?.let { getNumberFromWord(it) }}${lastMatch?.let { getNumberFromWord(it) }}".toInt())
        }

        return calibrations.sum()
    }

    private fun getNumberFromWord(value: String): Int {
        return if (value.toIntOrNull() != null) value.toInt() else when (value) {
            "one" -> 1
            "two" -> 2
            "three" -> 3
            "four" -> 4
            "five" -> 5
            "six" -> 6
            "seven" -> 7
            "eight" -> 8
            "nine" -> 9
            else -> throw IllegalArgumentException("Invalid number")
        }
    }
}