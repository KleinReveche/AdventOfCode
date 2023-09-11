package com.kleinreveche.adventofcode.twentytwo

import com.kleinreveche.adventofcode.Solver
import com.kleinreveche.adventofcode.Utils

/** --- Day 3: Rucksack Reorganization --- https://adventofcode.com/2022/day/3 */
class DayThree : Solver {

    private data class DayThreeItemList(val item1: String, val item2: String, val combined: String)

    override fun solve() {
        val (sum, groupedSum) = parseData()

        println(" --- 2022 Day 3: Rucksack Reorganization ---\n")
        println("   The sum of all item type priorities of all the Elves' rucksack are: $sum")
        println("   Also, all item type priorities of three grouped Elves' rucksack are: $groupedSum\n")
    }

    override fun parseData(): Pair<Int, Int> {
        var sum = 0
        var groupedSum = 0

        val items = Utils.readInput("twentytwo", "day03")!!.trim().lines()
        val separatedItems = mutableListOf<DayThreeItemList>()

        items.forEach { item ->
            val middleLineIndex = item.count() / 2
            val firstHalf = item.substring(0, middleLineIndex)
            val secondHalf = item.substring(middleLineIndex, item.count())
            separatedItems.add(DayThreeItemList(firstHalf, secondHalf, item))
        }

        separatedItems.forEach {
            val commonLetterValue = checkForCommonLetter(it.item1, it.item2)
            sum += commonLetterValue
        }

        separatedItems.chunked(3).forEach {
            val commonGroupedLetterValue = checkForCommonLetter(it[0].combined, it[1].combined, it[2].combined)
            groupedSum += commonGroupedLetterValue
        }

        return Pair(sum, groupedSum)
    }

    private fun checkForCommonLetter(vararg strings: String): Int {
        val charArrays = strings.map { it.toCharArray().toSet() }
        val commonLetter = charArrays.reduce { acc, set -> acc.intersect(set) }
        return when (commonLetter.first()) {
            in 'a'..'z' -> commonLetter.first() - 'a' + 1
            in 'A'..'Z' -> commonLetter.first() - 'A' + 27
            else -> throw IllegalArgumentException("Invalid character: $commonLetter.first()")
        }
    }

    override fun parseData(input: Int): Any {
        return 0
    }
}