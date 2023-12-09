package com.kleinreveche.adventofcode.twentytwo.day05

import com.kleinreveche.adventofcode.lib.ISolution
import com.kleinreveche.adventofcode.lib.Problem
import com.kleinreveche.adventofcode.lib.Year
import com.kleinreveche.adventofcode.showMore
import kotlin.collections.component1
import kotlin.collections.component2

@Problem(year = Year.TwentyTwo, day = "Day05", name = "Supply Stacks")
class Solution(private val input: String) : ISolution {
    private val data = parseData()
    private val crateMap = flipCrateTableMap(data.first)

    override fun partOne(): Any {
        var topCrateLetters = ""
        val finalCrateMap = moveCrates(crateMap, data.second)

        if (showMore) {
            println("   Supply Crates:")
            for (row in data.first) {
                print("   ")
                for (cell in row) {
                    print("[${cell.ifEmpty { " " }}] ")
                }
                println()
            }
            println("\n   Flipped Supply Crate Map:")
            for (row in crateMap) {
                print("   ")
                for (cell in row) {
                    print("[${cell.ifEmpty { " " }}] ")
                }
                println()
            }
            println("\n   Final Supply Crate Map")
            for (row in finalCrateMap) {
                print("   ")
                for (cell in row) {
                    print("[${cell.ifEmpty { " " }}] ")
                }
                println()
            }
            println()
        }

        finalCrateMap.forEach {
            topCrateLetters += it.last()
        }
        return "After the Rearrangement procedure was done, the top crates are: $topCrateLetters"
    }

    override fun partTwo(): Any {
        var topCrateLettersNotReversed = ""
        val finalCrateMapNotReversed = moveCrates(crateMap, data.second, false)

        if (showMore) {
            println("\n   Final Supply Crate Map with New Crate")
            for (row in finalCrateMapNotReversed) {
                print("   ")
                for (cell in row) {
                    print("[${cell.ifEmpty { " " }}] ")
                }
                println()
            }
            println()
        }

        finalCrateMapNotReversed.forEach {
            topCrateLettersNotReversed += it.last()
        }

        return "Due to the upgrade of the crane to the new CrateMover 9001, the new top crates are: $topCrateLettersNotReversed"
    }

    data class SupplyCrateMoves(val crateCount: Int, val originColumn: Int, val destColumn: Int)

    private fun parseData(): Pair<Array<Array<String>>, List<SupplyCrateMoves>> {
        val lines = input.lines()
        val separatedItems = mutableListOf<SupplyCrateMoves>()
        val crateTableMap = mutableListOf<Array<String>>()
        val crateTemp = mutableListOf<String>()

        lines.forEach { line ->
            if (line.startsWith("move")) {
                val (first, second) = line.trim().split(" from ")
                val crateCount = first.removePrefix("move ").toInt()
                val (originColumn, destColumn) = second.split(" to ")
                separatedItems.add(SupplyCrateMoves(crateCount, originColumn.toInt() - 1, destColumn.toInt() - 1))
            } else {
                if (!line.startsWith(" 1") && line.isNotEmpty()) {
                    val crate = StringBuilder()
                    line.forEachIndexed { index, letter ->
                        crate.append(letter)

                        if ((crate.length == 4 || index == 34)) {
                            if (crate[1].code == 32) crateTemp.add("")
                            else crateTemp.add(crate[1].toString())
                            crate.clear()
                        }
                    }
                    crateTableMap.add(crateTemp.toTypedArray())
                    crateTemp.clear()
                }
            }
        }
        return Pair(crateTableMap.toTypedArray(), separatedItems)
    }

    private fun moveCrates(
        crateTable: List<List<String>>,
        movesList: List<SupplyCrateMoves>,
        isReversed: Boolean = true,
        showMore: Boolean = false
    ): List<List<String>> {

        val filteredTable = crateTable.map { row -> row.filter { it.isNotBlank() } }.toMutableList()
        movesList.forEach {
            val n = it.crateCount
            val origin = it.originColumn
            val dest = it.destColumn

            val cratesToMove = filteredTable[origin].takeLast(n)
            filteredTable[origin] = filteredTable[origin].dropLast(n)
            filteredTable[dest] = filteredTable[dest] + if (isReversed) cratesToMove.asReversed() else cratesToMove

            if (showMore) {
                println("move $n from ${origin + 1} to ${dest + 1}")
                for (row in filteredTable) {
                    for (cell in row) {
                        print("[${cell.ifEmpty { " " }}] ")
                    }
                    println()
                }
                println()
            }
        }

        return filteredTable
    }


    private fun flipCrateTableMap(crateTable: Array<Array<String>>): List<List<String>> {
        val numRows = crateTable.size
        val numCols = crateTable[0].size

        val newTable = Array(numCols) { Array(numRows) { "" } }
        for (i in 0..<numRows) {
            for (j in 0..<numCols) {
                newTable[j][i] = crateTable[i][j]
            }
        }

        val newNumRows = newTable.size

        return List(newNumRows) { newTable[it].reversedArray().toList() }
    }
}