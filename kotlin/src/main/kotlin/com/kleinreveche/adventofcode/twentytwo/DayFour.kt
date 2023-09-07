package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Utils

//--- Day 4: Camp Cleanup --- https://adventofcode.com/2022/day/4
fun dayFour() {
    val data = readDayFourData()
    val (inRangeCount, overlappedCount) = checkForRange(data)

    println(" --- 2022 Day 4: Camp Cleanup ---\n")
    println("   The Assignment Pairs that one range fully contain the other are: $inRangeCount")
    println("   The Assignment Pairs where the ranges overlap are: $overlappedCount\n")
}

private data class DayFourRange(val range1: IntRange, val range2: IntRange)

private fun checkForRange(input: List<DayFourRange>): Pair<Int, Int> {
    var inRange = 0
    var overlap = 0

    input.forEach {

        val range1WithinRange2 = it.range1.first >= it.range2.first && it.range1.last <= it.range2.last
        val range2WithinRange1 = it.range2.first >= it.range1.first && it.range2.last <= it.range1.last
        val overlapToLeft = it.range1.last < it.range2.first
        val overlapToRight = it.range1.first > it.range2.last

        if (range1WithinRange2 || range2WithinRange1) inRange++
        if (!(overlapToLeft || overlapToRight)) overlap++
    }

    return Pair(inRange, overlap)
}
private fun readDayFourData(): List<DayFourRange> {
    val sections = Utils.readInput("twentytwo", "day04")!!.trim().lines()
    val separatedItems = mutableListOf<DayFourRange>()

    sections.forEach { section ->
        val (first, second) = section.split(",")
        val (firstValue, secondValue) = first.split("-")
        val (thirdValue, fourthValue) = second.split("-")
        val firstRange = (firstValue.toInt()..secondValue.toInt())
        val secondRange = (thirdValue.toInt()..fourthValue.toInt())
        separatedItems.add(DayFourRange(firstRange, secondRange))
    }

    return separatedItems
}