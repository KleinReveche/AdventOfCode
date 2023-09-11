package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 4: Camp Cleanup --- https://adventofcode.com/2022/day/4 */
class DayFour : Solver {
    override fun solve() {
        val (inRangeCount, overlappedCount) = parseData()

        println(" --- 2022 Day 4: Camp Cleanup ---\n")
        println("   The Assignment Pairs that one range fully contain the other are: $inRangeCount")
        println("   The Assignment Pairs where the ranges overlap are: $overlappedCount\n")
    }

    override fun parseData(): Pair<Int, Int> {
        var inRange = 0
        var overlap = 0
        val sections = Utils.readInput("twentytwo", "day04")!!.trim().lines()

        sections.forEach { section ->
            val (first, second) = section.split(",")
            val (firstValue, secondValue) = first.split("-")
            val (thirdValue, fourthValue) = second.split("-")

            val start1 = firstValue.toInt()
            val end1 = secondValue.toInt()
            val start2 = thirdValue.toInt()
            val end2 = fourthValue.toInt()

            if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1))
                inRange++
            if (!(end1 < start2 || start1 > end2)) overlap++
        }

        return Pair(inRange, overlap)
    }

    override fun parseData(input: Int): Any {
        return 0
    }
}