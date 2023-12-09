package com.kleinreveche.adventofcode.twentytwo.day01

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyTwo, day = "Day01", name = "Calorie Counting")
class Solution(private val input: String) : ISolution {

    private val records = parseData()

    override fun partOne(): Any {
        return "The Elf with most calories carried was Elf ${records.indexOf(records.max()) + 1} with ${records.max()}"
    }

    override fun partTwo(): Any {
        val recordsDescending = records.sortedDescending()
        val top3TotalCalories = recordsDescending[0] + recordsDescending[1] + recordsDescending[2]
        return "Top 3 Elves carrying the most calories: " +
                "Elf ${records.indexOf(recordsDescending[0])}, " +
                "Elf ${records.indexOf(recordsDescending[1])}, " +
                "Elf ${records.indexOf(recordsDescending[2])} " +
                "with a combined $top3TotalCalories"
    }

    private fun parseData(): MutableList<Int> {
        val recordsList = mutableListOf<Int>()
        var currentSum = 0
        val lines = input.trim().lines()

        lines.forEach { line ->
            if (line.isBlank()) {
                recordsList.add(currentSum)
                currentSum = 0
            } else {
                val number = line.trim().toIntOrNull() ?: 0
                currentSum += number
            }
        }
        recordsList.add(currentSum)
        return recordsList
    }
}