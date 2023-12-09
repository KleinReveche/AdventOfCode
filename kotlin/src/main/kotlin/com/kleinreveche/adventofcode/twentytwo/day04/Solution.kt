package com.kleinreveche.adventofcode.twentytwo.day04

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year

@Problem(year = Year.TwentyTwo, day = "Day04", name = "Camp Cleanup")
class Solution(private val input: String) : ISolution {
    private val data = parseData()

    override fun partOne(): Any {
        return "The Assignment Pairs that one range fully contain the other are: ${data.first}"
    }

    override fun partTwo(): Any {
        return "The Assignment Pairs where the ranges overlap are: ${data.second}"
    }

    private fun parseData(): Pair<Int, Int> {
        var inRange = 0
        var overlap = 0
        val sections = input.trim().lines()

        sections.forEach { section ->
            val (first, second) = section.split(",")
            val (firstValue, secondValue) = first.split("-")
            val (thirdValue, fourthValue) = second.split("-")

            val start1 = firstValue.toInt()
            val end1 = secondValue.toInt()
            val start2 = thirdValue.toInt()
            val end2 = fourthValue.toInt()

            if ((start1 >= start2 && end1 <= end2) || (start2 >= start1 && end2 <= end1)) inRange++
            if (!(end1 < start2 || start1 > end2)) overlap++
        }

        return Pair(inRange, overlap)
    }
}