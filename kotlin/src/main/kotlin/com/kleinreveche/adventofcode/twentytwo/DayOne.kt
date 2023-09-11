package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 1: Calorie Counting --- https://adventofcode.com/2022/day/1 */
class DayOne : Solver {
    override fun solve() {
        val records = parseData()
        val recordsDescending = records.sortedDescending()
        val top3TotalCalories = recordsDescending[0] + recordsDescending[1] + recordsDescending[2]

        println()
        println(" --- 2022 Day 1: Calorie Counting ---\n")
        println("   The Elf with most calories carried was Elf ${records.indexOf(records.max()) + 1} with ${records.max()}")
        println(
            "   Top 3 Elves carrying the most calories: " +
                    "Elf ${records.indexOf(recordsDescending[0])}, " +
                    "Elf ${records.indexOf(recordsDescending[1])}, " +
                    "Elf ${records.indexOf(recordsDescending[2])} " +
                    "with a combined $top3TotalCalories\n"
        )
    }

    override fun parseData(): MutableList<Int> {
        val recordsList = mutableListOf<Int>()
        var currentSum = 0
        val lines = Utils.readInput("twentytwo", "day01")!!.trim().lines()

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

    override fun parseData(input: Int): Any {
        return 0
    }
}